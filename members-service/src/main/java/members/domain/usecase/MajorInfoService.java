package members.domain.usecase;

import members.dto.MajorInfoDto;
import members.dto.MajorInfoSaveDto;

import java.util.List;

public interface MajorInfoService {

    List<MajorInfoDto> getAllMajorInfo();

    void saveMajorInfo(MajorInfoSaveDto majorInfoSaveDto);

    void deleteMajorInfo(Integer majorId);
}
