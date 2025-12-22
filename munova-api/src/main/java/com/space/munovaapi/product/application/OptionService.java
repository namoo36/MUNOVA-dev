package com.space.munovaapi.product.application;

import com.space.munovaapi.product.application.exception.OptionException;
import com.space.munovaapi.product.domain.Option;
import com.space.munovaapi.product.domain.Repository.OptionRepository;
import com.space.munovaapi.product.domain.enums.OptionCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptionService {
    private final OptionRepository OptionRepository;

    public boolean isExist(OptionCategory optionCategory, String optionName) {
        return OptionRepository.existsByOptionTypeAndOptionName(optionCategory, optionName);
    }

    public Option findByCategoryAndName(OptionCategory optionCategory, String optionName) {
        return OptionRepository.findByOptionTypeAndOptionName(optionCategory, optionName)
                .orElseThrow(() -> OptionException.badRequset("요청한 옵션을 찾을 수 없습니다."));
    }

    public Option saveOption(Option option) {
       return OptionRepository.save(option);
    }

    public Option findById(Long colorId) {
        return OptionRepository.findById(colorId)
                .orElseThrow(() -> OptionException.badRequset("요청한 옵션을 찾을 수 없습니다."));
    }
}
