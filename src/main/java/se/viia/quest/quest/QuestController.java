package se.viia.quest.quest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.viia.quest.api.QuestRequest;
import se.viia.quest.api.QuestResponse;
import se.viia.quest.util.ApiUtils;

import java.util.Optional;
import java.util.UUID;

/**
 * @author affe 2018-04-24
 */
@RestController
@RequestMapping("/api/quests")
public class QuestController {

    private final QuestService questService;

    @Autowired
    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping("")
    public Iterable<QuestResponse> getQuests(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        if (limit == null) {
            return ApiUtils.transform(questService.getQuests(), QuestResponse::new);
        }
        return ApiUtils.transform(questService.getQuests(Optional.ofNullable(page).orElse(0), limit), QuestResponse::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestResponse> getQuest(@PathVariable UUID id) {
        return questService.getQuest(id)
                .map(QuestResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public QuestResponse createQuest(@RequestBody QuestRequest request) {
        return new QuestResponse(questService.saveQuest(request.toQuest(null)));
    }

    @PutMapping("/{id}")
    public QuestResponse updateQuest(@PathVariable UUID id, @RequestBody QuestRequest request) {
        return new QuestResponse(questService.saveQuest(request.toQuest(id)));
    }


}
