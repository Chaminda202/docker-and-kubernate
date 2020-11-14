import { CommentRequest } from './../../comment/comment-request.model';
import { CommentService } from './../../comment/comment.service';
import { Comment } from './../../comment/comment.model';
import { PostService } from 'src/app/shared/post.service';
import { Post } from './../../home/post.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {
  postId: number;
  post: Post;
  commentForm: FormGroup;
  comments: Array<Comment> = [];
  commentRequest: CommentRequest;

  constructor(private activateRoute: ActivatedRoute, private postService: PostService,
              private commentService: CommentService) {
    this.postId = this.activateRoute.snapshot.params.id as number;
   }

  ngOnInit(): void {
    this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    });
    this.getCommetsByPost();
    this.initializePost();
    this.initializeCommentForm();
    this.initializeCommentRequest();
  }

  postComment() {
    this.commentRequest.text = this.text.value;
    this.commentService.createPost(this.commentRequest)
      .subscribe(data => {
        this.text.setValue('');
        this.getCommetsByPost();
      });
  }

  private getCommetsByPost() {
    this.commentService.getCommentByPost(this.postId)
          .subscribe(data => {
            this.comments = data;
          });
  }

  private initializeCommentForm() {
    this.commentForm = new FormGroup({
      postId: new FormControl(this.postId),
      text: new FormControl('', Validators.required)
    });
  }

  private initializeCommentRequest() {
    this.commentRequest = {
      postId: this.postId,
      text: ''
    };
  }

  private initializePost() {
    this.post = {
      id: this.postId,
      postName: '',
      url: '',
      description: '',
      voteCount: 0,
      userName: '',
      subredditName: '',
      commentCount: 0,
      duration: '',
      upVote: false,
      downVote: false
    };
  }

  get text() {
    return this.commentForm.get('text');
  }
}
