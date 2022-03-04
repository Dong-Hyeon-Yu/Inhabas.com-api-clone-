package com.inhabas.api.members.domain.usecase;

import com.inhabas.api.members.domain.entity.major.MajorInfo;
import com.inhabas.api.members.domain.entity.major.MajorInfoRepository;
import com.inhabas.api.members.dto.MajorInfoDto;
import com.inhabas.api.members.dto.MajorInfoSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MajorInfoServiceImpl implements MajorInfoService {

    private final MajorInfoRepository majorInfoRepository;

    public List<MajorInfoDto> getAllMajorInfo() {
        return majorInfoRepository.findAll().stream()
                .map(majorInfo -> new MajorInfoDto(majorInfo.getId(), majorInfo.getCollege(), majorInfo.getMajor()))
                .collect(Collectors.toList());
    }

    public void saveMajorInfo(MajorInfoSaveDto majorInfoSaveDto) {
        majorInfoRepository.save(
                new MajorInfo(majorInfoSaveDto.getCollege(), majorInfoSaveDto.getMajor())
        );
    }

    public void deleteMajorInfo(Integer majorId) {
        majorInfoRepository.deleteById(majorId);
    }
}
