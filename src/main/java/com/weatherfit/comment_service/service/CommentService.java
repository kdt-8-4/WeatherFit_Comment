package com.weatherfit.comment_service.service;

import com.weatherfit.comment_service.common.mapper.CommentMapper;
import com.weatherfit.comment_service.dto.CommentRepsonseDTO;
import com.weatherfit.comment_service.dto.CommentRequestDTO;
import com.weatherfit.comment_service.dto.ReplyRequestDTO;
import com.weatherfit.comment_service.dto.ReplyResponseDTO;
import com.weatherfit.comment_service.entity.Comment;
import com.weatherfit.comment_service.entity.Reply;
import com.weatherfit.comment_service.repository.CommentRepository;
import com.weatherfit.comment_service.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final CommentMapper commentMapper;

    public CommentRepsonseDTO writeComment(CommentRequestDTO commentRequestDTO) {
        Comment comment = commentMapper.DTOToComment(commentRequestDTO);
        Comment insertComment = commentRepository.save(comment);
        return commentMapper.commentToDTO(insertComment);
    }

    public ReplyResponseDTO writeReply(ReplyRequestDTO replyRequestDTO) {
        Comment comment = commentRepository.findById(replyRequestDTO.getCommentId());

        Reply reply = commentMapper.DTOToReply(replyRequestDTO);
        reply.setComment(comment);
        Reply insertReply = replyRepository.save(reply);
        return commentMapper.replyToDTO(insertReply);
    }

    public List<CommentRepsonseDTO> getCommentsByBoard(int boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        comments.forEach(comment -> comment.getReplyList().size());
//        for(Comment c : comments) {
//            System.out.println(c.toString());
//        }
        return commentMapper.commentsToDTOList(comments);
    }

    public Boolean removeComment(int commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        Comment comment = findComment.get();
        comment.setStatus(0);
        Comment result = commentRepository.save(comment);
        return result == null ? false : true;
    }

    public Boolean removeReply(int replyId) {
        Optional<Reply> findReply = replyRepository.findById(replyId);
        Reply reply = findReply.get();
        reply.setStatus(0);
        Reply result = replyRepository.save(reply);
        return result == null ? false : true;
    }
}
