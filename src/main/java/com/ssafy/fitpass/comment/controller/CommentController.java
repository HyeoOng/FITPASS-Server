package com.ssafy.fitpass.comment.controller;

import com.ssafy.fitpass.ai.service.AiService;
import com.ssafy.fitpass.comment.dto.RetCommentDto;
import com.ssafy.fitpass.comment.dto.PostCommentDto;
import com.ssafy.fitpass.comment.dto.PutCommentDto;
import com.ssafy.fitpass.comment.service.CommentService;
import com.ssafy.fitpass.user.dto.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cmt")
public class CommentController {

    CommentService commentService;
    AiService aiService;

    public CommentController(CommentService commentService, AiService aiService) {
        this.commentService = commentService;
        this.aiService = aiService;
    }

    @PostMapping
    public Map<String, String> createComment(@RequestBody PostCommentDto comment, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();

        System.out.println("comment: " + comment);

        HttpSession session = request.getSession(false);
        if(session == null){
            response.put("msg", "로그인부터 해주세요.");
            return response;
        }
        RetUser retUser = (RetUser) session.getAttribute("user");
        comment.setUserId(retUser.getUserId());
        System.out.println(retUser);

        // Post 객체의 title과 content 검증
        if (!aiService.isContentAppropriate(comment.getComment())) {
//            throw new IllegalArgumentException("비난, 혐오, 욕설이 포함된 글은 작성할 수 없습니다.");
            System.out.println("감지.. 댓글...");
            response.put("msg", "fail with ai");
            return response;
        }

        if(commentService.createComment(comment)){
            response.put("msg", "댓글을 성공적으로 등록하였습니다.");
        }else {
            response.put("msg", "죄송합니다. 댓글 등록에 실패하였습니다. <br> 잠시 후 다시 시도해주세요.");
        }
        return response;
    }

    @GetMapping
    public List<RetCommentDto> getCommentsbyPost(@RequestParam int postId) {
        return commentService.getCommentsByPost(postId);
    }

    @PostMapping("/update")
    public Map<String, String> modifyComment(@RequestBody PutCommentDto comment) {
        Map<String, String> response = new HashMap<>();
        if(commentService.modifyComment(comment)){
            response.put("msg", "댓글을 성공적으로 수정하였습니다.");
        }else{
            response.put("msg", "댓글 수정에 실패하였습니다. <br> 잠시 후 다시 시도해주세요.");
        }
        return response;
    }

    @PostMapping("/delete")
    public Map<String, String> removeComment(@RequestBody int commentId) {
        Map<String, String> response = new HashMap<>();
        if(commentService.removeComment(commentId)){
            response.put("msg", "댓글을 성공적으로 삭제하였습니다.");
        }else{
            response.put("msg", "댓글 삭제를 실패하였습니다. <br> 잠시 후 다시 시도해주세요.");
        }
        return response;
    }
}
