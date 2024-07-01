package me.qunqun.doctor.service;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.vo.HospitalVO;
import me.qunqun.doctor.repo.HospitalRepository;
import me.qunqun.doctor.specification.HospitalSpecification;
import me.qunqun.shared.entity.po.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Transactional
    public List<HospitalVO> queryHospitalVO(Integer hospitalId, String name, String address, String phone) {
        try {
            List<Hospital> hospitalList = hospitalRepository.findAll(HospitalSpecification.getHospitalsByCriteria(hospitalId, name, address, phone));
            List<HospitalVO> hospitalVOList = new ArrayList<>();
            for (Hospital hospital : hospitalList) {
                HospitalVO hospitalVO = new HospitalVO();
                hospitalVO = hospitalVO.fromEntity(hospital);
                hospitalVOList.add(hospitalVO);
            }
            return hospitalVOList;
        } catch (Exception e) {
            log.error("queryHospitalVO error: {}", e.getMessage());
            return null;
        }
    }
}
