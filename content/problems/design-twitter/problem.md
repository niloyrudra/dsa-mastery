---
id: "design-twitter"
title: "Design Twitter"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/design-twitter/"
patterns: ["priority-queue-applications", "heap-fundamentals", "hash-map-lookup"]
algorithms: []
dataStructures: ["heap", "hash-table"]
templates: ["heap-template-ts", "hash-map-template-ts"]
prerequisites: ["heap", "hash-table"]
relatedProblems: ["median-finder", "kth-largest-element-in-a-stream", "top-k-frequent-elements"]
status: "PUBLISHED"
orderIndex: 38
reasonForSelection: "Max-heap for news feed; heap of heaps for followee posts"
---

# Problem Statement

Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and see the 10 most recent tweets in the user's news feed.

Implement the `Twitter` class:

- `Twitter()` Initializes your twitter object.
- `void postTweet(int userId, int tweetId)` Composes a new tweet with ID `tweetId` by the user `userId`. Each call to this function will be made with a unique `tweetId`.
- `List<Integer> getNewsFeed(int userId)` Retrieves the 10 most recent tweet IDs in the user's news feed. Each item in the news feed must be posted by users who the user follows or by the user themself. Tweets must be ordered from most recent to least recent.
- `void follow(int followerId, int followeeId)` The user with ID `followerId` started following the user with ID `followeeId`.
- `void unfollow(int followerId, int followeeId)` The user with ID `followerId` started unfollowing the user with ID `followeeId`.

## Constraints

- 1 ≤ userId, followerId, followeeId ≤ 500
- 0 ≤ tweetId ≤ 10⁴
- All the tweets have **unique** IDs.
- At most 3 × 10⁴ calls will be made to `postTweet`, `getNewsFeed`, `follow`, and `unfollow`.

## Examples

**Example 1:**
```
Input:
["Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"]
[[],[1,5],[1],[1,2],[2,6],[1],[1,2],[1]]
Output:
[null,null,[5],null,null,[6,5],null,[5]]
Explanation:
Twitter twitter = new Twitter();
twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
twitter.getNewsFeed(1);  // User 1's news feed should return [5].
twitter.follow(1, 2);    // User 1 follows user 2.
twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
twitter.getNewsFeed(1);  // User 1's news feed should return [6, 5].
twitter.unfollow(1, 2);  // User 1 unfollows user 2.
twitter.getNewsFeed(1);  // User 1's news feed should return [5].
```

## Variations

### Variation 1: Median Finder
**Name**: Median Finder

**What Changed**: Find median of stream instead of news feed.

**Original Approach Works**: Dual heaps (max-heap + min-heap) for median.

**What Breaks**: Different heap structure.

**New Constraint**: Return median of all numbers added so far.

**Algorithm Changes**: Balance two heaps, median from tops.

**Complexity Changes**: Time O(log n) add, O(1) find, Space O(n).

### Variation 2: Top K Frequent Elements
**Name**: Top K Frequent Elements

**What Changed**: Find top k frequent elements.

**Original Approach Works**: Frequency map + min-heap of size k.

**What Breaks**: Different domain (frequency vs recency).

**New Constraint**: Return k most frequent elements.

**Algorithm Changes**: Count frequencies, heap for top k.

**Complexity Changes**: Time O(n log k), Space O(n).