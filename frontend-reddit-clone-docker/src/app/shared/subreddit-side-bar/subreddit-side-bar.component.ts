import { Component, OnInit } from '@angular/core';
import { SubredditService } from '../../subreddit/subreddit.service';
import { Subreddit } from '../../subreddit/subreddit.model';

@Component({
  selector: 'app-subreddit-side-bar',
  templateUrl: './subreddit-side-bar.component.html',
  styleUrls: ['./subreddit-side-bar.component.css']
})
export class SubredditSideBarComponent implements OnInit {
  subreddits: Array<Subreddit>;
  displayViewAll: boolean;
  constructor(private subredditService: SubredditService) { }

  ngOnInit(): void {
    this.subredditService.allSubreddits().subscribe(data => {
      if (data.length >= 4) {
        this.subreddits = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.subreddits = data;
      }
    });
  }
}
