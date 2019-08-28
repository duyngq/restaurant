package ampos.miniproject.restaurant.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MimeTypeUtils;

import ampos.miniproject.restaurant.BaseTestCase;
import ampos.miniproject.restaurant.dto.BillDTO;
import ampos.miniproject.restaurant.dto.BillItemDTO;
import ampos.miniproject.restaurant.dto.request.BillItemRequestDTO;
import ampos.miniproject.restaurant.dto.request.BillRequestDTO;
import ampos.miniproject.restaurant.repository.BillItemRepository;
import ampos.miniproject.restaurant.util.RestaurantConstants;

/**
 * Test case for bill
 */
public class BillResourceTestCase extends BaseTestCase {
    @Autowired
    private BillItemRepository billItemRepos;

    /**
     * Create a new bill with bill items details
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void testCreateBill() throws IOException, Exception {
        Set<BillItemRequestDTO> billItems = new HashSet<>();
        BillItemRequestDTO billItem = new BillItemRequestDTO();
        billItem.setId(1L);
        billItem.setMenuId(1L);
        billItem.setQuantity(10);
        billItems.add(billItem);

        billItem = new BillItemRequestDTO();
        billItem.setId(1L);
        billItem.setMenuId(1L);
        billItem.setQuantity(20);
        billItems.add(billItem);

        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setBillItems(billItems);
        // compare
        MvcResult result = mockMvc.perform(post(BillResource.BILL_MAPPING)
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(objectToJson(billRequest)))
                .andExpect(status().is(201)).andReturn();
        // check database
        BillDTO bill = jsonToObject(result.getResponse().getContentAsString(), BillDTO.class);
        assertEquals(new BigDecimal(9000.00).setScale(2), bill.getTotal());
        assertEquals(2, bill.getBillItems().size());
    }

    /**
     * Get a bill by id
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void testGetBill() throws IOException, Exception {
        MvcResult result = mockMvc.perform(get(BillResource.BILL_MAPPING + "/1")).andExpect(status().is(200))
                .andReturn();
        // @formatter:off
        String expectedBill = "{\"id\":1," + "\"billItems\":[{" + "\"id\":1,\"quantity\":1," + "\"menu\":{"
                + "\"id\":1," + "\"name\":\"Hawaiian Pizza\","
                + "\"description\":\"All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.\","
                + "\"imageUrl\":\"https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg\","
                + "\"price\":300.00," + "\"details\":[\"Italian\",\"Ham\",\"Pineapple\"]},"
                + "\"orderedTime\":\"2019-11-11T04:11:11.000+0000\"," + "\"billId\":1," + "\"subTotal\":300.00}],"
                + "\"total\":300.00}";
        // @formatter:on
        assertEquals(expectedBill, result.getResponse().getContentAsString());
    }

    /**
     * Add a new bill item to an existing bill
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void testCreateBillItem() throws IOException, Exception {
        Set<BillItemRequestDTO> billItems = new HashSet<>();
        BillItemRequestDTO billItem = new BillItemRequestDTO();
        billItem.setMenuId(1L);
        billItem.setQuantity(1);
        billItems.add(billItem);

        billItem = new BillItemRequestDTO();
        billItem.setMenuId(2L);
        billItem.setQuantity(10);
        billItems.add(billItem);

        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setBillItems(billItems);

        MvcResult result = mockMvc.perform(put(BillResource.BILL_MAPPING + "/1")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(objectToJson(billRequest)))
                .andExpect(status().is(200)).andReturn();
        BillDTO resultData = jsonToObject(result.getResponse().getContentAsString(), BillDTO.class);
        assertEquals(new BigDecimal(3800.00).setScale(2), resultData.getTotal());
        assertEquals(2, resultData.getBillItems().size());
    }

    /**
     * Update bill item in an existing bill
     *
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void testUpdateBillItem() throws IOException, Exception {
        assertTrue(billItemRepos.findById((long) 1).get().getQuantity() == 1);
        Set<BillItemRequestDTO> billItems = new HashSet<>();
        BillItemRequestDTO billItem = new BillItemRequestDTO();
        billItem.setId(1L);
        billItem.setMenuId(1L);
        billItem.setQuantity(10);
        billItems.add(billItem);

        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setBillItems(billItems);

        MvcResult result = mockMvc.perform(put(BillResource.BILL_MAPPING + "/1")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(objectToJson(billRequest)))
                .andExpect(status().is(200)).andReturn();
        BillDTO actualData = jsonToObject(result.getResponse().getContentAsString(), BillDTO.class);
        assertEquals(new BigDecimal(3000.00).setScale(2), actualData.getTotal());
        assertEquals(1, actualData.getBillItems().size());
        BillItemDTO billItemsDTO = actualData.getBillItems().iterator().next();
        assertEquals(10, billItemsDTO.getQuantity());

        //Compare to Db
        result = mockMvc.perform(get(BillResource.BILL_MAPPING + "/1")).andExpect(status().is(200))
                .andReturn();
        BillDTO billInDb = jsonToObject(result.getResponse().getContentAsString(), BillDTO.class);
        assertEquals(new BigDecimal(3000.00).setScale(2), billInDb.getTotal());
        assertEquals(1, billInDb.getBillItems().size());
        BillItemDTO billItemsInDb = billInDb.getBillItems().iterator().next();
        assertEquals(10, billItemsInDb.getQuantity());
    }

    /**
     * Delete bill by id
     *
     * @throws Exception
     */
    @Test
    public void testDeleteBillById() throws Exception {
        assertTrue(billItemRepos.existsById((long) 1));
        MvcResult result = mockMvc.perform(delete(BillResource.BILL_MAPPING + "/1")).andExpect(status().is(400))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(RestaurantConstants.ACTION_NOT_SUPPORTED));
        // check database
        result = mockMvc.perform(get(BillResource.BILL_MAPPING + "/1")).andExpect(status().is(200))
                .andReturn();
        BillDTO billInDb = jsonToObject(result.getResponse().getContentAsString(), BillDTO.class);
        assertNotNull(billInDb);
        assertEquals(new BigDecimal(300.00).setScale(2), billInDb.getTotal());
        assertEquals(1, billInDb.getBillItems().size());
    }
}
