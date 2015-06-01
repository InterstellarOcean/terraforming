# Release procedure

This is a manual release procedure instruction/checklist.


## Prepare

1. Update README's javadoc link with version (see 'site release', below)


## Release

1. `mvn -Prelease release:clean release:prepare release:perform`
1. Promote manually to Maven Central with <https://oss.sonatype.org/>
1. `git push --follow-tags`


### Site release

Generate and publish the site under `RELEASE_VERSION` path.

```sh
RELEASE_VERSION=...

cd target/checkout
mvn site
git checkout gh-pages
mv target/site v/${RELEASE_VERSION}
git add v
find v | grep "/\.[^/]*$"
cat _config.yml
git commit -m "Project site v${RELEASE_VERSION}"
git push
cd ../..
```


##  Coverity update (by travis)

1. Forward branch: `coverity_scan` (e.g. by reset) to the release tag
1. Travis CI will take care from that point


## Publish

1. Update wiki [home page](https://github.com/InterstellarOcean/terraforming/wiki)'s javadoc link with version (see 'site release', above)
1. Add wiki's [latest news](https://github.com/InterstellarOcean/terraforming/wiki) entry
1. Tweet the news by [@InterstellarOcn](https://twitter.com/InterstellarOcn)
