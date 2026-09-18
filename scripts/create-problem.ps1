param(
    [string]$ProblemId,
    [string]$Title,
    [string]$Difficulty = "MEDIUM",
    [string[]]$Patterns,
    [string[]]$Algorithms,
    [string[]]$DataStructures,
    [string[]]$Templates,
    [string[]]$Prerequisites,
    [string[]]$RelatedProblems,
    [int]$OrderIndex,
    [string]$ReasonForSelection = "Core DSA problem for interview preparation"
)

$ProblemDir = "Content/problems/$ProblemId"
$SolutionsDir = "$ProblemDir/solutions"
$TemplatesDir = "$ProblemDir/templates"

New-Item -ItemType Directory -Path $ProblemDir -Force | Out-Null
New-Item -ItemType Directory -Path $SolutionsDir -Force | Out-Null
New-Item -ItemType Directory -Path $TemplatesDir -Force | Out-Null

$PatternsStr = ($Patterns | ForEach-Object { "`"$_`"" }) -join ", "
$AlgorithmsStr = ($Algorithms | ForEach-Object { "`"$_`"" }) -join ", "
$DataStructuresStr = ($DataStructures | ForEach-Object { "`"$_`"" }) -join ", "
$TemplatesStr = ($Templates | ForEach-Object { "`"$_`"" }) -join ", "
$PrerequisitesStr = ($Prerequisites | ForEach-Object { "`"$_`"" }) -join ", "
$RelatedProblemsStr = ($RelatedProblems | ForEach-Object { "`"$_`"" }) -join ", "

$Utf8NoBom = New-Object System.Text.UTF8Encoding($false)

$ProblemMd = @"
---
id: "$ProblemId"
title: "$Title"
difficulty: "$Difficulty"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/$ProblemId/"
patterns: [$PatternsStr]
algorithms: [$AlgorithmsStr]
dataStructures: [$DataStructuresStr]
templates: [$TemplatesStr]
prerequisites: [$PrerequisitesStr]
relatedProblems: [$RelatedProblemsStr]
status: "PUBLISHED"
orderIndex: $OrderIndex
reasonForSelection: "$ReasonForSelection"
---

# Problem Statement

TODO: Add problem statement for $Title

## Constraints

- TODO: Add constraints

## Examples

**Example 1:**
```
Input: TODO
Output: TODO
```

## Variations

TODO: Add variations
"@

[IO.File]::WriteAllText("$ProblemDir/problem.md", $ProblemMd, $Utf8NoBom)

$TestCasesYaml = @"
- name: "Basic Example 1"
  description: "Standard test case"
  input:
    nums: [1, 2, 3]
    target: 6
  expected: [0, 2]
  type: "BASIC"
  hidden: false

- name: "Edge: Empty/Single"
  description: "Edge case with minimal input"
  input:
    nums: [1]
    target: 1
  expected: [0, 0]
  type: "EDGE_CASE"
  hidden: false

- name: "Performance: Large Input"
  description: "Large input for performance testing"
  input:
    nums: "GENERATE: range(10000)"
    target: 19999
  expected: [9999, 10000]
  type: "PERFORMANCE"
  hidden: true
"@

[IO.File]::WriteAllText("$ProblemDir/test-cases.yaml", $TestCasesYaml, $Utf8NoBom)

$ReferencesYaml = @"
- title: "$Title - LeetCode"
  url: "https://leetcode.com/problems/$ProblemId/"
  type: "PROBLEM_SOURCE"
  relevantConcept: "$Title Problem"
  verifies: "Problem constraints and examples"

- title: "$Title - CP Algorithms"
  url: "https://cp-algorithms.com/"
  type: "TECH_RESOURCE"
  relevantConcept: "Algorithm explanation"
  verifies: "Algorithm reference"
"@

[IO.File]::WriteAllText("$ProblemDir/references.yaml", $ReferencesYaml, $Utf8NoBom)

$SolutionWorst = @"
/**
 * Approach: Brute Force (WORST)
 * Time: O(n^2) or worse, Space: O(1)
 * 
 * TODO: Implement brute force approach
 * 
 * WHY IT WORKS: Exhaustive search checks all possibilities.
 * 
 * BOTTLENECK: Too slow for large inputs.
 * 
 * INSIGHT TO NEXT: Can optimize with better data structure or algorithm.
 */
function bruteForce(input: any): any {
    // TODO: Implement
    return null;
}
"@

[IO.File]::WriteAllText("$SolutionsDir/brute-force.ts", $SolutionWorst, $Utf8NoBom)

$SolutionBetter = @"
/**
 * Approach: Optimized (BETTER)
 * Time: O(n log n) or O(n), Space: O(n) or O(1)
 * 
 * TODO: Implement optimized approach
 * 
 * WHY IT WORKS: Uses better algorithm/data structure.
 * 
 * BOTTLENECK: Still has room for improvement.
 * 
 * INSIGHT TO NEXT: Can achieve optimal with different technique.
 */
function betterSolution(input: any): any {
    // TODO: Implement
    return null;
}
"@

[IO.File]::WriteAllText("$SolutionsDir/better.ts", $SolutionBetter, $Utf8NoBom)

$SolutionBest = @"
/**
 * Approach: Optimal (BEST)
 * Time: O(n) or O(log n), Space: O(1) or O(n)
 * 
 * TODO: Implement optimal approach
 * 
 * WHY IT WORKS: Uses the key insight/pattern for this problem.
 * 
 * BOTTLENECK: Optimal for this problem class.
 * 
 * INSIGHT TO NEXT: Variation handling for extended problems.
 */
function optimalSolution(input: any): any {
    // TODO: Implement
    return null;
}
"@

[IO.File]::WriteAllText("$SolutionsDir/optimal.ts", $SolutionBest, $Utf8NoBom)

$TemplateMd = @"
---
id: "$ProblemId-template"
name: "$Title Template"
language: "TYPESCRIPT"
purpose: "Template for $Title problem"
when_to_reach_for_it: "When solving $Title and similar problems"
prerequisites: "Understanding of $($Patterns -join ', ') pattern"
mental_model: "TODO: Add mental model"
code: |
// TODO: Add template code for $Title

explanation: "TODO: Add explanation"
invariant: "TODO: Add invariant"
common_modifications: "TODO: Add modifications"
common_mistakes: "TODO: Add common mistakes"
time_complexity: "TODO"
space_complexity: "TODO"
pattern: "$($Patterns[0])"
status: "DRAFT"
---
# $Title Template

TODO: Add template code and explanation
"@

[IO.File]::WriteAllText("$TemplatesDir/template.md", $TemplateMd, $Utf8NoBom)

Write-Host "Created problem: $ProblemId"