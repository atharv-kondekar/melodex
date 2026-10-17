let allAlbums = [];


// ==========================================
// PAGE INITIALIZATION
// ==========================================

document.addEventListener("DOMContentLoaded", () => {

    requireLogin();

    loadAlbums();

    document
        .getElementById("refresh")
        .addEventListener("click", loadAlbums);

    document
        .getElementById("search")
        .addEventListener("input", renderAlbums);

});


// ==========================================
// LOAD ALBUMS
// ==========================================

async function loadAlbums() {

    const grid =
        document.getElementById("albumGrid");

    grid.innerHTML =
        '<div class="message">Loading albums...</div>';

    try {

        const response =
            await api("/albums");

        allAlbums =
            unwrap(response) || [];

        renderAlbums();

    } catch (error) {

        grid.innerHTML = "";

        showMessage(
            error.message,
            true
        );
    }
}


// ==========================================
// RENDER ALBUMS
// ==========================================

function renderAlbums() {

    const search =
        document.getElementById("search");

    const query =
        search.value
            .toLowerCase()
            .trim();


    const albums =
        allAlbums.filter(album => {

            const title =
                String(album.title || "")
                    .toLowerCase();

            const thumbnail =
                String(album.thumbnail || "")
                    .toLowerCase();

            return title.includes(query)
                || thumbnail.includes(query);

        });


    const grid =
        document.getElementById("albumGrid");


    if (!albums.length) {

        grid.innerHTML =
            '<div class="message">No albums found.</div>';

        return;
    }


    grid.innerHTML =
        albums.map(album => {

            const file =
                String(
                    album.thumbnail || ""
                ).trim();


            // ==================================
            // IMPORTANT:
            // YOUR SIR'S BACKEND USES /image/
            // ==================================

            const url =
                file
                    ? "http://localhost:8081/albums/image/"
                        + encodeURIComponent(file)
                    : "";


            const lower =
                file.toLowerCase();


            let media =
                '<span class="muted">♫</span>';


            // ==================================
            // VIDEO
            // ==================================

            if (
                url &&
                /\.(mp4|webm|ogg|mov|m4v)$/i
                    .test(lower)
            ) {

                media = `
                    <video
                        src="${url}"
                        controls
                        preload="metadata">
                    </video>
                `;

            }


            // ==================================
            // AUDIO
            // ==================================

            else if (
                url &&
                /\.(mp3|wav|m4a|aac|flac)$/i
                    .test(lower)
            ) {

                media = `
                    <audio
                        src="${url}"
                        controls>
                    </audio>
                `;

            }


            // ==================================
            // IMAGE
            // ==================================

            else if (url) {

                media = `
                    <img
                        src="${url}"
                        alt="${escapeHtml(
                            album.title || "Album"
                        )}">
                `;

            }


            // ==================================
            // ALBUM CARD
            // ==================================

            return `

                <article class="card album">

                    <div class="album-media">

                        ${media}

                    </div>


                    <div class="album-info">

                        <strong>
                            ${escapeHtml(
                                album.title || "Untitled"
                            )}
                        </strong>


                        <small>
                            ID #${album.id ?? "—"}
                            ·
                            ${escapeHtml(
                                file || "No media"
                            )}
                        </small>


                        ${
                            url
                                ? `
                                    <div class="actions">

                                        <button
                                            class="btn open"
                                            data-url="${url}">

                                            Open media

                                        </button>

                                    </div>
                                `
                                : ""
                        }

                    </div>

                </article>

            `;

        }).join("");


    // ==================================
    // OPEN MEDIA BUTTON
    // ==================================

    document
        .querySelectorAll(".open")
        .forEach(button => {

            button.addEventListener(
                "click",
                () => {

                    window.open(
                        button.dataset.url,
                        "_blank"
                    );

                }
            );

        });

}


// ==========================================
// MESSAGE
// ==========================================

function showMessage(
    text,
    error = false
) {

    const element =
        document.getElementById("message");

    if (!element) {
        return;
    }

    element.textContent =
        text;

    element.className =
        `message ${
            error
                ? "error"
                : "success"
        }`;
}