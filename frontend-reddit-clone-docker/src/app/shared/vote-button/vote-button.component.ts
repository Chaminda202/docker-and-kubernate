import { throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { VoteType } from './../vote-type.enum';
import { Post } from './../../home/post.model';
import { Component, OnInit, Input } from '@angular/core';
import { faArrowUp, faArrowDown} from '@fortawesome/free-solid-svg-icons';
import { VoteService } from '../vote.service';
import { PostService } from '../post.service';
import { AuthService } from 'src/app/auth/shared/auth.service';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {
  @Input() post: Post;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  // use a typescript enum value in an Angular, not assign the value another property
  voteType = VoteType;

  constructor(private voteService: VoteService, private toastrService: ToastrService,
              private postService: PostService, private authService: AuthService) { }

  ngOnInit(): void {
  }

  votePost(voteType: VoteType) {
    console.log('Vote value ' + voteType);
    const votePayload = {
      postId: this.post.id,
      voteType
    };
    if (this.authService.isLoggedIn) {
      this.voteService.vote(votePayload).subscribe(data => {
        // manually update in front end without retrieve update details
        /* if (voteType === VoteType.UPVOTE) {
          this.post.voteCount = this.post.voteCount + 1;
          this.post.upVote = true;
          this.post.downVote = false;
        } else {
          this.post.voteCount = this.post.voteCount - 1;
          this.post.upVote = false;
          this.post.downVote = true;
        } */
        /* this.post.voteCount = voteType === VoteType.UPVOTE ? this.post.voteCount + 1
                                  : this.post.voteCount - 1;
        console.log('After Vote count ' + this.post.voteCount);
        */
        this.postService.getPost(this.post.id).subscribe(resp => {
          this.post = resp;
        });
      }, errorResponse => {
        // console.log('Vote Error Response ' + JSON.stringify(errorResponse));
        if (errorResponse.error && errorResponse.error.details) {
          this.toastrService.error(errorResponse.error.details[0]);
        }
        throwError(errorResponse);
      });
    } else {
      this.toastrService.error('Please first logging to vote');
    }
  }
}
