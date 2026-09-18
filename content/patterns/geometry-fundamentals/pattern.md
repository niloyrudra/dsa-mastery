---
id: "geometry-fundamentals"
name: "Geometry Fundamentals"
category: "Geometry"
definition: "2D matrix/array manipulation as geometric operations: rotation, spiral traversal, boundary traversal, coordinate transformation, bounding boxes."
when_to_use: "Rotate image; spiral matrix; set matrix zeroes; game of life; valid sudoku; matrix diagonal traversal; island perimeter; rectangle overlap; convex hull."
why_it_works: "Matrix indices map to coordinates. Rotation = transpose + reverse. Spiral = simulate boundaries shrinking. In-place operations use marker values or encoding."
intuition: "Layer by layer for rotation/spiral. For in-place: use first row/col as markers, or encode two values in one cell. Boundary tracking for spiral."
invariant: "Rotation: element at (i,j) goes to (j, n-1-i). Spiral: boundaries shrink after each complete loop. Zero marking: first row/col store zero flags."
strengths: "O(1) extra space possible for many; O(n*m) time optimal for matrix; visual/intuitive; in-place techniques reusable."
weaknesses: "Index calculations error-prone; in-place encoding tricky; not all operations O(1) space; edge cases for non-square matrices."
recognition_signals:
  - "rotate image"
  - "spiral matrix"
  - "set matrix zeroes"
  - "game of life"
  - "valid sudoku"
  - "matrix diagonal"
  - "island perimeter"
  - "rectangle overlap"
  - "convex hull"
common_failure_modes:
  - "Wrong rotation formula"
  - "Spiral boundary conditions (single row/col)"
  - "Overwriting values before reading in in-place"
  - "Not handling empty matrix"
  - "Off-by-one in boundary updates"
generalizations: "3D matrix operations; computational geometry algorithms (convex hull, line intersection); graphics transformations; image processing kernels."
time_complexity: "O(n * m) for matrix traversal"
space_complexity: "O(1) for in-place, O(n*m) for visited/auxiliary"
time_reasoning: "Each cell visited constant number of times."
space_reasoning: "In-place uses matrix itself for markers; visited array for spiral."
status: "PUBLISHED"
---
# Geometry Fundamentals Pattern

## Definition
2D matrix/array manipulation as geometric operations: rotation, spiral traversal, boundary traversal, coordinate transformation, bounding boxes.

## When to Use
- Rotate image
- Spiral matrix
- Set matrix zeroes
- Game of life
- Valid sudoku
- Matrix diagonal traversal
- Island perimeter
- Rectangle overlap
- Convex hull

## Why It Works
Matrix indices map to coordinates. Rotation = transpose + reverse. Spiral = simulate boundaries shrinking. In-place operations use marker values or encoding.

## Intuition
Layer by layer for rotation/spiral. For in-place: use first row/col as markers, or encode two values in one cell. Boundary tracking for spiral.

## Invariant
Rotation: element at (i,j) goes to (j, n-1-i). Spiral: boundaries shrink after each complete loop. Zero marking: first row/col store zero flags.

## Strengths
- O(1) extra space possible for many
- O(n*m) time optimal for matrix
- Visual/intuitive
- In-place techniques reusable

## Weaknesses
- Index calculations error-prone
- In-place encoding tricky
- Not all operations O(1) space
- Edge cases for non-square matrices

## Recognition Signals
- "rotate image"
- "spiral matrix"
- "set matrix zeroes"
- "game of life"
- "valid sudoku"
- "matrix diagonal"
- "island perimeter"
- "rectangle overlap"
- "convex hull"

## Common Failure Modes
- Wrong rotation formula
- Spiral boundary conditions (single row/col)
- Overwriting values before reading in in-place
- Not handling empty matrix
- Off-by-one in boundary updates

## Generalizations
3D matrix operations; computational geometry algorithms (convex hull, line intersection); graphics transformations; image processing kernels.

## Complexity
- Time: O(n * m) for matrix traversal
- Space: O(1) for in-place, O(n*m) for visited/auxiliary