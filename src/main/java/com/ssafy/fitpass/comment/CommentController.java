package com.ssafy.fitpass.comment;

import com.ssafy.fitpass.user.RetUser;
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

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Map<String, String> createComment(@RequestBody Comment comment, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        RetUser retUser = (RetUser) session.getAttribute("user");
        comment.setUserId(retUser.getUserId());

        Map<String, String> response = new HashMap<>();
        System.out.println("잘 들어옴: " + comment);
        if(commentService.createComment(comment)){
            response.put("msg", "댓글을 성공적으로 등록하였습니다.");
        }else {
            response.put("msg", "죄송합니다. 댓글 등록에 실패하였습니다. <br> 잠시 후 다시 시도해주세요.");
        }
        return response;
    }

    @GetMapping
    public List<Comment> getCommentsbyPost(@RequestParam int postId) {
        return commentService.getCommentsByPost(postId);
    }

    @PostMapping("/update")
    public Map<String, String> modifyComment(@RequestBody Comment comment) {
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
