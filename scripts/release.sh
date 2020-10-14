#!/usr/bin/env bash
set -e

# Use full history for release
git checkout -B "${TRAVIS_BRANCH}"
# Add email to link commits to user
git config user.email "${GIT_COMMITTER_EMAIL}"
git config user.name "${GIT_COMMITTER_NAME}"

release_type=$1

if [ -z $release_type ]; then
    echo "Please provide a release type."
    exit 1
elif [ $release_type != "community" -a $release_type != "enterprise" ]; then
    echo "The provided release type is not valid."
    exit 1
fi

if [ -z ${RELEASE_VERSION} ] || [ -z ${DEVELOPMENT_VERSION} ];
    then echo "Please provide a Release and Development verison"
         exit 1
else
    mvn --batch-mode
    -Dusername="${GITHUB_USERNAME}" \
    -Dpassword="${GITHUB_PASSWORD}" \
    -DreleaseVersion=${RELEASE_VERSION} \
    -DdevelopmentVersion=${DEVELOPMENT_VERSION} \
    -DskipTests -D${release_type} -DuseReleaseProfile=false \
    -Prelease-${release_type} release:clean release:prepare release:perform
fi