package com.inhabas.api.members.domain.usecase;

import com.inhabas.api.members.dto.MajorInfoDto;
import com.inhabas.api.members.dto.MajorInfoSaveDto;

import java.util.List;

public interface MajorInfoService {

    List<MajorInfoDto> getAllMajorInfo();

    void saveMajorInfo(MajorInfoSaveDto majorInfoSaveDto);

    void deleteMajorInfo(Integer majorId);
}
