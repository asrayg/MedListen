package Service;

import com.example.medlistenback.MedlistenbackApplication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord save(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }

    public List<MedicalRecord> findByUserId(Long userId) {
        return medicalRecordRepository.findByUserId(userId);
    }
}
