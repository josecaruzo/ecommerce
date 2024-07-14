package br.com.fiap.mscart.service.mapper;

import br.com.fiap.mscart.entity.Item;
import br.com.fiap.mscart.entity.request.ItemRequest;
import br.com.fiap.mscart.entity.response.ItemResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemMapper {

	public Item itemRequestToItemEntity(ItemRequest itemRequest){
		return Item.builder()
				.productId(itemRequest.getProductId())
				.quantity(itemRequest.getQuantity())
				.build();
	}

	public ItemResponse itemEntityToItemResponse(Item item){
		return ItemResponse.builder()
				.productId(item.getProductId())
				.name(item.getName())
				.quantity(item.getQuantity())
				.build();
	}
}
