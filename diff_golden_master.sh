#!/bin/bash

mvn compile test-compile exec:java --quiet | head -n -1 > golden.tmp
diff -y --color=always --left-column goldenMasterTemplate.txt <(cat golden.tmp)
echo ""
diff -sq goldenMasterTemplate.txt <(cat golden.tmp)

exit 0
