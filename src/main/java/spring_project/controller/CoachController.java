    package spring_project.controller;

    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    import spring_project.dto.CoachRequets;
    import spring_project.service.CoachSevice;

    import java.io.IOException;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequiredArgsConstructor
    public class CoachController {

        private final CoachSevice coachSevice;

        @GetMapping("/api/useradmin-all-coach") // Cần TOKEN ADMIN và USER để có thể coi
        public ResponseEntity<List<CoachRequets>> getAllCoach() {
            List<CoachRequets> allCoachs = coachSevice.getAllCoaches();
            return new ResponseEntity<>(allCoachs, HttpStatus.OK);
        }

        @GetMapping("/api/useradmin-all-coach/{coachId}") // Cần TOKEN ADMIN và USER để có thể coi
        public ResponseEntity<CoachRequets> getCoachById(@PathVariable Long coachId) {
            CoachRequets coachById = coachSevice.getCoacheById(coachId);
            return new ResponseEntity<>(coachById, HttpStatus.OK);
        }

        @PostMapping("/api/api-coach/create-coach") // Cần TOKEN ADMIN để tạo
        public ResponseEntity<Map<String , Object>> createCoach(
                @RequestParam(value = "image" , required = false) MultipartFile file,
                @RequestParam(value = "coachName" , required = false) String coachName,
                @RequestParam(value = "licensePlateNumberCoach" , required = false) Long licensePlateNumberCoach) {
            try {
                CoachRequets coachRequets = new CoachRequets(coachName , licensePlateNumberCoach);
                Map<String,Object> dataCoach = coachSevice.createCoach(file , coachRequets);
                return new ResponseEntity<>(dataCoach, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(Map.of("error" , e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }

        @PutMapping("/api/api-coach/update-coach/{coachId}") // Cần TOKEN ADMIN để cập nhật
        public ResponseEntity<Map<String,Object>> updateCoach(
                @PathVariable Long coachId,
                @RequestParam(value = "image" , required = false) MultipartFile file,
                @RequestParam(value = "coachName" , required = false) String coachName,
                @RequestParam(value = "licensePlateNumberCoach" , required = false) Long licensePlateNumberCoac) {
            try {
              CoachRequets coachRequets = new CoachRequets(coachName , licensePlateNumberCoac);
              coachSevice.updateCoach(coachId , file , coachRequets);
              return ResponseEntity.ok(Map.of("message", "Driver updated successfully"));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", e.getMessage()));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Failed to update image: " + e.getMessage()));
            }
        }

        @DeleteMapping("/api/api-coach/delete-coach/{coachId}") // Cần TOKEN ADMIN để xoá
        public ResponseEntity<Map<String,Object>> deleteCoach(@PathVariable Long coachId) {
            try {
                coachSevice.deleteCoach(coachId);
                return new ResponseEntity<>(Map.of("message", "Driver deleted successfully"), HttpStatus.OK);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(Map.of("error" , e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }

    }
