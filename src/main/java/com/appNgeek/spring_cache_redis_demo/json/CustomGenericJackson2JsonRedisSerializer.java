package com.appNgeek.spring_cache_redis_demo.json;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

public class CustomGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {

	@Override
	public byte[] serialize(@Nullable Object source) throws SerializationException {

		if (source != null && source instanceof PageImpl) {
			PageImpl<?> page = (PageImpl<?>) source;
			SerializablePageRequest serializablePageRequest = new SerializablePageRequest(page.getPageable().getPageNumber(),
					page.getPageable().getPageSize(), SerializablePageSort.by(page.getPageable().getSort().iterator().next()));
			SerializablePageImpl<?> serializablePageImpl = new SerializablePageImpl<>(page.getContent(), serializablePageRequest,
					page.getTotalElements());
			source = serializablePageImpl;
		}

		return super.serialize(source);

	}

}
