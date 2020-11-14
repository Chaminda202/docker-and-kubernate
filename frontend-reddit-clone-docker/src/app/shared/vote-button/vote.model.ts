import { VoteType } from './../vote-type.enum';
export interface Vote {
  postId: number;
  username?: string;
  voteType: VoteType;
}
