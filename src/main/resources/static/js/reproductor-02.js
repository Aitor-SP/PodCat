window.onload = function() {
    let srcAudio = 'audios/keneda.mp3'
    let playing = false;

    let audio = document.getElementById("audio");
    let contenidorAnimacio = document.getElementById("contenidor-animacio");
    let boto = document.getElementById("play");
    let timeline = document.getElementById("timeline");
    let progress = document.createElement("div");
    let currentTimeSpan = document.getElementById("current-time");
    let totalTimeSpan = document.getElementById("total-time");

    let animacio = lottie.loadAnimation({
        container: contenidorAnimacio,
        renderer: 'svg',
        loop: false,
        autoplay: false,
        path: '/json/toPause.json'
    })

    audio.setAttribute("src", srcAudio);
    progress.classList.add("progress");
    timeline.appendChild(progress);

    boto.addEventListener('click', play);

    audio.addEventListener('timeupdate', function() {
        let percent = (audio.currentTime / audio.duration) * 100;
        progress.style.width = percent + '%';
        currentTimeSpan.innerText = formatTime(audio.currentTime) + " / ";
    });

    audio.addEventListener('loadedmetadata', function() {
        currentTimeSpan.innerText = formatTime(audio.currentTime) + " / ";
        totalTimeSpan.innerText = formatTime(audio.duration);
    });

    timeline.addEventListener('click', function(event) {
        let width = this.offsetWidth;
        let clickX = event.offsetX;
        let duration = audio.duration;

        audio.currentTime = (clickX / width) * duration;
    });

    function play() {
        if (!playing) {
            audio.play();
            animacio.setDirection(1);
            animacio.play();
            playing = true;
        } else {
            audio.pause();
            animacio.setDirection(-1);
            animacio.play();
            playing = false;
        }
    }

    function formatTime(seconds) {
        let date = new Date(null);
        date.setSeconds(seconds);
        return date.toISOString().substr(11, 8);
    }
};