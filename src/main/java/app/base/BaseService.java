package app.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BaseService {
	private final BaseRepository baseRepository;

	public Integer getCount() {
		return baseRepository.getCount();
	}
}
