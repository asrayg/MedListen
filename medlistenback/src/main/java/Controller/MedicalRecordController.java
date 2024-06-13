package Controller;

import com.example.medlistenback.MedlistenbackApplication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecord> saveRecord(@RequestBody MedicalRecord record) {
        return ResponseEntity.ok(medicalRecordService.save(record));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MedicalRecord>> getUserRecords(@PathVariable Long userId) {
        return ResponseEntity.ok(medicalRecordService.findByUserId(userId));
    }
}

