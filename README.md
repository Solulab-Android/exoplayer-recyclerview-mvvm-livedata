# CoolExoplayer-in-recyclerview-using-mvvm-livedata-

# CoolExoplayer 

> ***Video list auto playback made simple, specially built for RecyclerView***

<img src="/extra/web_hi_res_5412.png" width="256">

## Menu

* [Features](#features)
* [Getting start, basic implementation](#getting-start-basic-implementation)
* [License](#license)

## Features

Core:
  - Auto start/pause Media playback on user interaction: scroll, open/close App.
  - Optional playback position save/restore (default: disabled).
    - If enabled, CoolPlayer will also save/restore them on Configuration change (orientation change, multi-window mode ...).
  - Customizable playback component: either MediaPlayer or ExoPlayer will work. CoolPlayer comes with default helper classes to support these 2.
  - Customizable player selector: custom the selection of the player to start, among many other players.
    - Which in turn support single/multiple players.
  - Powerful default implementations, enough for various use cases.

Plus alpha:
  - First class support for ExoPlayer 2. 


## Getting start implementation

#### 1. Update module build.gradle.

Latest version:
 [ ![Download]() ](https://bintray.com/beta/#/tofik7878/CoolExoPlayer/CoolExoplayer/1.2.1)
 
```groovy
ext {
  latest_release = '1.2.1' // TODO check above for latest version
}

dependencies {
  implementation 'com.tofik.coolexoplayer:coolexoplayer:1.2.1'
   implementation("com.google.android.exoplayer:exoplayer:2.9.5") {
        exclude group: 'com.android.support'
    }  // to get ExoPlayer support
}
```

Using snapshot:

Update this to root's ``build.gradle``

```gradle
allprojects {
  repositories {
    google()
    jcenter()
   
  }
  
  // TODO anything else
}
```

Application's build.gradle

```groovy
ext {
 maybe
}

dependencies {
  implementation 'com.tofik.coolexoplayer:coolexoplayer:1.2.1'
   implementation("com.google.android.exoplayer:exoplayer:2.9.5") {
        exclude group: 'com.android.support'
    }  // to get ExoPlayer support
}
```

#### 2. Using ```Container``` in place of Video list/RecyclerView. 

```xml
<com.tofik.coolexoplayer.exoplayer.cool.widget.Container
  android:id="@+id/my_videos"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
/>
```

#### 3. Implement ```CoolPlayer``` to ViewHolder that should be a Video player.

<details><summary>Sample Coolplayer implementation (click to expand)</summary>
<p>

```java

class HomeViewHolder(
    val binding: ItemHomeBinding,
    itemView: View,
    selector: PressablePlayerSelector?,
    val mcontex: Context,
    val homeAdapter: HomeAdapter
) :
    RecyclerView.ViewHolder(binding.root), CoolPlayer, CoolPlayer.OnVolumeChangeListener {

    lateinit var list: MutableList<HomeData>
    var helper: ExoPlayerViewHelper? = null
    lateinit var mediaUri: Uri
    var playerView: PlayerView = binding.player
    ///for mute/unmute all videos of list
    var data = Data  //////data class that store the boolean variable ismute=true/false


    override fun onVolumeChanged(volumeInfo: VolumeInfo) {
        data.isMute = volumeInfo.isMute
    }


    override fun getPlayerView(): View {
        return playerView
    }

    override fun getCurrentPlaybackInfo(): PlaybackInfo {
        return if (helper != null) helper!!.latestPlaybackInfo else PlaybackInfo()
    }

    override fun initialize(
        container: Container,
        playbackInfo: PlaybackInfo
    ) {
        if (helper == null) {
            helper = ExoPlayerViewHelper(this, mediaUri, data.isMute)

        }

        helper!!.initialize(container, playbackInfo, data.isMute)
        helper!!.addOnVolumeChangeListener(this)

    }

    override fun play() {
        if (helper != null) helper!!.play()
    }

    override fun pause() {
        if (helper != null) helper!!.pause()
    }

    override fun isPlaying(): Boolean {
        return helper != null && helper!!.isPlaying
    }

    override fun release() {
        if (helper != null) {
            helper!!.release()
            helper = null
        }
    }

    override fun wantsToPlay(): Boolean {
        return CoolUtil.visibleAreaOffset(this, itemView.parent) >= 0.85
    }

    override fun getPlayerOrder(): Int {
        return adapterPosition
    }

    override fun toString(): String {
        return "ExoPlayer{" + hashCode() + " " + adapterPosition + "}"
    }


    fun bind(data: HomeData, list: MutableList<HomeData>) {
        binding.position = adapterPosition
        binding.holder = this
        binding.data = data
        this.list = list
        mediaUri = Uri.parse(data.video)
    }

    init {
        if (selector != null) playerView.setControlDispatcher(ExoPlayerDispatcher(selector, this))
    }


}

```

## Proguard

If you need to enable proguard in your app, put below rules to your proguard-rules.pro

```proguard
-keepclassmembernames class com.google.android.exoplayer2.ui.PlayerControlView {
  java.lang.Runnable hideAction;
  void hideAfterTimeout();
}
```
## License

> Copyright 2017 eneim@Eneim Labs, nam@ene.im

> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at

>        http://www.apache.org/licenses/LICENSE-2.0

> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
