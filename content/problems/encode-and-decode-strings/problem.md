---
id: "encode-and-decode-strings"
title: "Encode and Decode Strings"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/encode-and-decode-strings/"
patterns: ["hash-map-lookup"]
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts"]
prerequisites: ["array", "hash-table"]
relatedProblems: ["group-anagrams", "valid-anagram", "serialize-and-deserialize-binary-tree"]
status: "PUBLISHED"
orderIndex: 6
reasonForSelection: "Length-prefixed encoding; delimiter-safe serialization"
---

# Problem Statement

Design an algorithm to encode a list of strings to a single string. The encoded string is then sent over the network and is decoded back to the original list of strings.

Machine 1 (sender) has the function:
```
string encode(vector<string> strs) {
  // ... your code
  return encoded_string;
}
```

Machine 2 (receiver) has the function:
```
vector<string> decode(string s) {
  // ... your code
  return strs;
}
```

So Machine 1 does:
```
string encoded_string = encode(strs);
```

Machine 2 does:
```
vector<string> strs2 = decode(encoded_string);
```

`strs2` in Machine 2 should be the same as `strs` in Machine 1.

Implement the `encode` and `decode` methods.

You are not allowed to use `eval` or `serialize` methods.

## Constraints

- 1 ≤ strs.length ≤ 200
- 0 ≤ strs[i].length ≤ 200
- `strs[i]` contains any possible characters out of 256 valid ASCII characters.

## Examples

**Example 1:**
```
Input: ["neet","code","love","you"]
Output: ["neet","code","love","you"]
```

**Example 2:**
```
Input: [""]
Output: [""]
```

## Variations

### Variation 1: Serialize and Deserialize Binary Tree
**Name**: Serialize and Deserialize Binary Tree

**What Changed**: Binary tree instead of string array.

**Original Approach Works**: Preorder traversal with null markers.

**What Breaks**: Tree structure vs flat array.

**New Constraint**: Serialize tree to string, deserialize back.

**Algorithm Changes**: Preorder with "null" markers, queue for deserialization.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Group Anagrams
**Name**: Group Anagrams

**What Changed**: Group strings by anagram signature.

**Original Approach Works**: Hash map with sorted string as key.

**What Breaks**: Grouping vs encoding.

**New Constraint**: Return grouped anagrams.

**Algorithm Changes**: Sort each string, use as hash map key.

**Complexity Changes**: Time O(n * k log k), Space O(n * k).