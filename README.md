# Line Grouping Application

## Overview

This Java application reads a text file containing multiple lines.
Each line contains of semicolon-separated substrings (columns).
The program groups unique lines into non-overlapping groups based on the following rule:

> Two lines belong to the same group if they share one or more **non-empty matching values in the same column**.

### Example

Given three lines:

```
"111";"123";"222"
"200";"123";"100"
"300";;"100"
```

All three lines belong to the same group because:
- Lines 1 and 2 share the value `123` in the second column.
- Lines 2 and 3 share the value `100` in the third column.

Whereas these lines:

```
"100";"200";"300"
"200";"300";"100"
```

Do **not** belong to the same group, because matching values contain in different columns.

---

## Input File Format

- Each line consists of one or more values, separated by semicolons. Each value is enclosed in double quotes.
- Lines may have varying numbers of columns.
- Duplicate lines may be present, but duplicates will be ignored and processed only once.
- Invalid lines (e.g., malformed quotes or corrupted data) are skipped.

---

## Output Format

The program writes the found groups to an output file (created in the same directory as the input file) in the following format:

```
Number of groups with more than one element: X

Group 1
line1
line2
line3
...

Group 2
line1
line2
...
```

Groups are sorted by descending size (largest groups first).

---

## How to Build and Run

1. Navigate to the project root directory (where `pom.xml` is located).

2. Build the project:

      ```bash
      mvn clean package
      ```

3. Run the JAR with your input file:

   ```bash
   java -jar target/union-find.jar /path/to/input-file.txt
   ```

---

## Notes

- The program accepts input files in `.txt` or `.gz` format.
- The output file includes number of groups that contain more than one element.

---
