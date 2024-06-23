package com.nlu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlu.exception.NotFoundException;
import com.nlu.model.dto.request.OptionRequest;
import com.nlu.model.dto.response.OptionResponse;
import com.nlu.model.entity.Option;
import com.nlu.repository.OptionRepository;
@Service
public class OptionService {

	@Autowired private OptionRepository optionRepository;
	
	@Transactional
	public OptionResponse updateOption(Long optionId, OptionRequest request) {
		Option option = optionRepository.findById(optionId)
				.orElseThrow(() -> new NotFoundException("Option not found"));
		option.setNameOption(request.getContent());
		option.setCorrect(request.getIsCorrect());
		return OptionResponse.fromEntity(optionRepository.save(option));
	}
}
