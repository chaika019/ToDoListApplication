package by.chaika19.controller.secured;

import by.chaika19.entity.RecordStatus;
import by.chaika19.entity.dto.RecordsContainerDto;
import by.chaika19.service.RecordService;
import by.chaika19.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {

    private final UserService userService;
    private final RecordService recordService;

    @Autowired
    public PrivateAccountController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
    }

    @GetMapping()
    public String getMainPage(Model model, @RequestParam(value = "filter", required = false) String filterMode) {
        RecordsContainerDto containerDto = recordService.findAllRecords(filterMode);
        model.addAttribute("userName", containerDto.getUserName());
        model.addAttribute("records", containerDto.getRecords());
        model.addAttribute("numberOfDoneRecords",  containerDto.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", containerDto.getNumberOfActiveRecords());
        return "private/account-page";
    }

    @PostMapping("/add-record")
    public String addRecord(@RequestParam("title") String title) {
        recordService.saveRecord(title);
        return "redirect:/account";
    }

    @PostMapping("/make-record-done")
    public String makeRecordDone(@RequestParam("id") int id,
                                 @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.updateRecord(id, RecordStatus.DONE);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }

    @PostMapping("/delete-record")
    public String deleteRecord(@RequestParam("id") int id,
                               @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }
}
