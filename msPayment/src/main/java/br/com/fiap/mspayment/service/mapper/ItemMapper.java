package br.com.fiap.mspayment.service.mapper;

import br.com.fiap.mspayment.entity.Item;
import br.com.fiap.mspayment.entity.response.ItemResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemMapper {

	public ItemResponse itemEntityToItemResponse(Item item){
		return ItemResponse.builder()
				.productId(item.getProductId())
				.name(item.getName())
				.unitPrice(item.getUnitPrice())
				.quantity(item.getQuantity())
				.itemTotalPrice(item.getItemTotalPrice())
				.build();
	}
}
