package ampos.miniproject.restaurant.resource;

import io.swagger.annotations.ApiOperation;

import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ampos.miniproject.restaurant.dto.BillDTO;
import ampos.miniproject.restaurant.dto.request.BillRequestDTO;
import ampos.miniproject.restaurant.dto.statistic.TotalStatisticDTO;
import ampos.miniproject.restaurant.exception.ApplicationException;
import ampos.miniproject.restaurant.service.BillService;
import ampos.miniproject.restaurant.util.BillItemRequestView;
import ampos.miniproject.restaurant.util.RestaurantConstants;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * REST controller for bills
 *
 */
@RestController
@RequestMapping(BillResource.BILL_MAPPING)
public class BillResource extends GenericResource<BillRequestDTO, BillDTO, Long, BillService> {
    public static final String BILL_MAPPING = "/bills";

    public BillResource(BillService billService) {
        super(billService);
    }

    String getMapping() {
        return BILL_MAPPING;
    }

    @Override
    @PostMapping
    public ResponseEntity<BillDTO> create(@JsonView(BillItemRequestView.Add.class) @RequestBody BillRequestDTO request)
            throws ApplicationException, URISyntaxException {
        return super.create(request);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<BillDTO> update(@PathVariable Long id,
            @JsonView(BillItemRequestView.Edit.class) @RequestBody BillRequestDTO request) throws ApplicationException {
        return super.update(id, request);
    }

    @Override
    @ApiOperation(value = "Delete bill by id", response = ResponseEntity.class)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws ApplicationException {
        throw new ApplicationException(RestaurantConstants.ACTION_NOT_SUPPORTED);
    }

    /**
     * GET /bills/check : Check all bills and bill items
     *
     *
     * @return
     * @throws ApplicationException
     */
    @ApiOperation(value = "Check all bills and menus in bills", response = ResponseEntity.class)
    @GetMapping("/checkbill")
    public ResponseEntity<TotalStatisticDTO> checkBillAndBillItem() throws ApplicationException {
        TotalStatisticDTO result = this.service.getBillAndBillItemStatistic();
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }
}
