# Github Firebase Ios sdk Issue
GitHub web API to retrieve all open issues associated with the firebase-ios-sdk repository.



Documentation for the GitHub issues API can be found here: http://developer.github.com/v3/issues

The URL to fetch issues associated with the firebase-ios-sdk repository can be found here: https://api.github.com/repos/firebase/firebase-ios-sdk/issues

Comments of an Issue: https://api.github.com/repos/firebase/firebase-ios-sdk/issues/3228/comments


Display a list of issues per user

Order by most-recently updated issue first

Issue titles and first 140 characters of the issue body should be shown in the list

Allow the user to tap an issue to display next screen (detail screen) containing all comments for that issue.

All the comments should be shown on the detail screen. The complete comment body and username of each comment author should be shown on this screen.

Implement persistent storage in the application for caching data so that the issues are only fetched once in 24 hours. The persistent storage should only contain the latest data and any old data can be discarded.
