package com.ssafy.fitpass.comment.controller;

import com.ssafy.fitpass.ai.service.AiService;
import com.ssafy.fitpass.comment.dto.RetCommentDto;
import com.ssafy.fitpass.comment.dto.PostCommentDto;
import com.ssafy.fitpass.comment.dto.PutCommentDto;
import com.ssafy.fitpass.comment.service.CommentService;
import com.ssafy.fitpass.exception.RegDBException;
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
    public Map<String, Object> createComment(@RequestBody PostCommentDto comment, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        HttpSession session = request.getSession(false);
        if(session == null){
            response.put("flag", false);
            response.put("code", "SPL0001");
            return response;
        }
        try{
            RetUser retUser = (RetUser) session.getAttribute("user");
            if(retUser == null){
                response.put("flag", false);
                response.put("code", "SPL0001");
                return response;
            }
            comment.setUserId(retUser.getUserId());
//            System.out.println(retUser);

            // Post 객체의 title과 content 검증
            if (!aiService.isContentAppropriate(comment.getComment())) {
                response.put("flag", false);
                response.put("code", "UAL0005");
                return response;
            }

            if(commentService.createComment(comment)){
                response.put("flag", true);
            }else {
                response.put("flag", false);
                response.put("code", "DAL0001");
            }
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @GetMapping
    public Map<String, Object> getCommentsbyPost(@RequestParam int postId) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<RetCommentDto> comments = commentService.getCommentsByPost(postId);
            response.put("comments", comments);
            response.put("flag", true);
            return response;
        } catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @PostMapping("/update")
    public Map<String, Object> modifyComment(@RequestBody PutCommentDto comment) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (commentService.modifyComment(comment)) {
                response.put("flag", true);
            } else {
                response.put("flag", false);
                response.put("code", "DAL0001");
            }
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        }catch (Exception e){
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @PostMapping("/delete")
    public Map<String, Object> removeComment(@RequestBody int commentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (commentService.removeComment(commentId)) {
                response.put("flag", true);
            } else {
                response.put("flag", false);
                response.put("code", "DAL0001");
            }
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }
}
