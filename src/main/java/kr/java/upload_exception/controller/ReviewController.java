package kr.java.upload_exception.controller;

import jakarta.validation.Valid;
import kr.java.upload_exception.model.entity.Review;
import kr.java.upload_exception.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 목록 페이지
     * GET /reviews
     */
    @GetMapping
    // org.springframework.ui.Model;
    public String list(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        model.addAttribute("pageName", "리뷰 목록");
        return "review/list";
    }

    /**
     * 리뷰 상세 페이지
     * GET /reviews/{id}
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Review review = reviewService.findById(id);
        model.addAttribute("review", review);
        model.addAttribute("pageName", "리뷰 상세");
        return "review/detail";
    }

    /**
     * 리뷰 작성 폼 페이지
     * GET /reviews/new
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("pageName", "리뷰 작성");
        return "review/form";
    }

    /**
     * 리뷰 등록 처리
     * POST /reviews
     *
     * @param review 폼에서 입력받은 리뷰 데이터 (@ModelAttribute: 폼 데이터를 객체에 자동 바인딩)
     * @param bindingResult Validation 결과
     * @param imageFile 업로드된 이미지 파일 (@RequestParam: 요청 파라미터를 직접 받음)
     * @param model forward 시 데이터 전달
     * @param redirectAttributes 리다이렉트 시 데이터 전달용 (일회성 메시지)
     */
    @PostMapping
    public String create(@Valid @ModelAttribute Review review,
                         BindingResult bindingResult,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageName", "리뷰 작성");
            model.addAttribute("bindingResult", bindingResult);
            return "review/form"; // 검증 미통과 시 폼 페이지로 포워드
        }

        reviewService.create(review, imageFile);

        // Flash Attribute: 리다이렉트 후 한 번만 사용되는 데이터
        redirectAttributes.addFlashAttribute("message", "리뷰가 등록되었습니다.");

        return "redirect:/reviews";
    }


    /**
     * 리뷰 수정 폼 페이지
     * GET /reviews/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Review review = reviewService.findById(id);
        model.addAttribute("review", review);
        model.addAttribute("pageName", "리뷰 수정");
        return "review/edit";
    }

    /**
     * 리뷰 수정 처리
     * POST /reviews/{id}/edit
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute Review review,
                         BindingResult bindingResult,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageName", "리뷰 수정");
            model.addAttribute("bindingResult", bindingResult);
            return "review/edit"; // 검증 미통과 시 수정 페이지로 포워드
        }
        reviewService.update(id, review, imageFile);
        redirectAttributes.addFlashAttribute("message", "리뷰가 수정되었습니다.");

        return "redirect:/reviews/" + id;
    }

    /**
     * 리뷰 삭제 처리
     * POST /reviews/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewService.delete(id);
        redirectAttributes.addFlashAttribute("message", "리뷰가 삭제되었습니다.");

        return "redirect:/reviews";
    }
}
