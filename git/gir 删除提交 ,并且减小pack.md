#

<https://stackoverflow.com/questions/13716658/how-to-delete-all-commit-history-in-github>

<https://stackoverflow.com/questions/11050265/remove-large-pack-file-created-by-git>

```sh
git checkout --orphan latest_branch
git add -A
git commit -am "commit message"
git branch -D main
git branch -m main
git push -f origin main
# clane package
git filter-branch --index-filter 'git rm -r --cached --ignore-unmatch unwanted_filename_or_folder' --prune-empty
# git reflog expire --expire-unreachable=all --all





```
