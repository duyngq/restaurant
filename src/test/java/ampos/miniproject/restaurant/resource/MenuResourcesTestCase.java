package ampos.miniproject.restaurant.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MimeTypeUtils;

import ampos.miniproject.restaurant.BaseTestCase;
import ampos.miniproject.restaurant.dto.MenuDTO;
import ampos.miniproject.restaurant.dto.request.MenuRequestDTO;
import ampos.miniproject.restaurant.util.RestaurantConstants;

/**
 * Test case for menu
 *
 */
public class MenuResourcesTestCase extends BaseTestCase {

    /**
     * Create a new menu
     *
     * @throws Exception
     */
    @Test
    public void testCreateMenu() throws Exception {
        MenuRequestDTO menu = new MenuRequestDTO("Food 2",
                "All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style",
                "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
                new BigDecimal(300), Arrays.asList(new String[] { "Ham", "Chili" }));
        // compare
        MvcResult result = mockMvc.perform(post(MenuResource.MENUS_MAPPING)
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(objectToJson(menu)))
                .andExpect(status().is(201)).andReturn();
        MenuDTO resultData = jsonToObject(result.getResponse().getContentAsString(), MenuDTO.class);
        assertEquals(menu.getName(), resultData.getName());
        assertEquals(menu.getDescription(), resultData.getDescription());
        assertEquals(menu.getImageUrl(), resultData.getImageUrl());
        assertEquals(menu.getPrice(), resultData.getPrice());
        assertEquals(menu.getDetails(), resultData.getDetails());
        // check database
        result = mockMvc.perform(get(MenuResource.MENUS_MAPPING + "/4")).andExpect(status().is(200)).andReturn();

        MenuDTO returnedMenu = jsonToObject(result.getResponse().getContentAsString(), MenuDTO.class);
        assertEquals(menu.getName(), returnedMenu.getName());
        assertEquals(menu.getDescription(), returnedMenu.getDescription());
        assertEquals(menu.getImageUrl(), returnedMenu.getImageUrl());
        assertEquals(menu.getPrice().setScale(2), returnedMenu.getPrice());
    }

    /**
     * Get data of a menu
     *
     * @throws Exception
     */
    @Test
    public void testGetMenu() throws Exception {
        MenuDTO expectedResult = new MenuDTO((long) 1, "Hawaiian Pizza",
                "All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.",
                "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
                new BigDecimal(300.00).setScale(2), Arrays.asList(new String[] { "Italian", "Ham", "Pineapple" }));
        // compare
        MvcResult result = mockMvc.perform(get(MenuResource.MENUS_MAPPING + "/1")).andExpect(status().is(200))
                .andReturn();
        assertEquals(objectToJson(expectedResult), result.getResponse().getContentAsString());
    }

    /**
     * Update data for a menu
     *
     * @throws Exception
     */
    @Test
    public void testUpdateMenu() throws Exception {
        // expected data
        MenuRequestDTO input = new MenuRequestDTO("Oolong tea edit",
                "All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style",
                "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg",
                new BigDecimal(300), Arrays.asList(new String[] { "Italian", "Ham", "Pineapple" }));
        // compare
        MvcResult result = mockMvc.perform(put(MenuResource.MENUS_MAPPING + "/1")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(objectToJson(input)))
                .andExpect(status().is(200)).andReturn();
        MenuDTO resultData = jsonToObject(result.getResponse().getContentAsString(), MenuDTO.class);
        assertEquals(input.getName(), resultData.getName());
        assertEquals(input.getDescription(), resultData.getDescription());
        assertEquals(input.getImageUrl(), resultData.getImageUrl());
        assertEquals(input.getPrice(), resultData.getPrice());
        assertEquals(input.getDetails(), resultData.getDetails());
        // check database
        MvcResult menuInDb = mockMvc.perform(get(MenuResource.MENUS_MAPPING + "/1")).andExpect(status().is(200))
                .andReturn();
        MenuDTO nenuData = jsonToObject(result.getResponse().getContentAsString(), MenuDTO.class);
        assertEquals(input.getName(), nenuData.getName());
        assertEquals(input.getDescription(), nenuData.getDescription());
        assertEquals(input.getImageUrl(), nenuData.getImageUrl());
        assertEquals(input.getPrice(), nenuData.getPrice());
    }

    /**
     * Delete a menu
     *
     * @throws Exception
     */
    @Test
    public void testDeleteMenu() throws Exception {
        MvcResult result = mockMvc
                .perform(delete(MenuResource.MENUS_MAPPING + "/2").contentType(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200)).andReturn();
        assertEquals("", result.getResponse().getContentAsString());
        // check database
        MvcResult menuInDb = mockMvc.perform(get(MenuResource.MENUS_MAPPING + "/2")).andExpect(status().is(400))
                .andReturn();
        assertTrue(menuInDb.getResponse().getContentAsString().contains(RestaurantConstants.ITEM_NOT_FOUND));
    }
}
