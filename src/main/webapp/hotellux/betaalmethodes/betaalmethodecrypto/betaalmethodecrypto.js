document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("cryptoForm");
    const idInput = document.getElementById("betaalmethode_id");
    const walletInput = document.getElementById("walletadres");
    const muntInput = document.getElementById("muntsoort");

    const errorId = document.getElementById("idError");
    const errorWallet = document.getElementById("walletError");
    const errorMunt = document.getElementById("muntError");
    const resultMessage = document.getElementById("resultMessage");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Reset fouten
        errorId.textContent = "";
        errorWallet.textContent = "";
        errorMunt.textContent = "";
        resultMessage.textContent = "";

        const id = idInput.value.trim();
        const walletadres = walletInput.value.trim();
        const muntsoort = muntInput.value.trim().toUpperCase();

        let valid = true;

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            errorId.textContent = "Geef een geldig ID op.";
            valid = false;
        }

        if (!walletadres || walletadres.length < 10) {
            errorWallet.textContent = "Walletadres moet minimaal 10 tekens hebben.";
            valid = false;
        }

        if (!muntsoort || muntsoort.length < 2) {
            errorMunt.textContent = "Geef een geldige muntsoort op.";
            valid = false;
        }

        if (!valid) return;

        try {
            const exists = await fetch(`http://localhost:8080/api/betaalmethodes/${id}`);
            if (!exists.ok) {
                errorId.textContent = "Betaalmethode-ID bestaat niet.";
                return;
            }

            const cryptoPayload = {
                id: parseInt(id),
                walletAdres: walletadres,
                muntsoort: muntsoort
            };

            const res = await fetch(`http://localhost:8080/api/betaalmethodecryptos/create`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cryptoPayload)
            });

            if (res.ok) {
                resultMessage.textContent = "Betaalmethode Crypto succesvol toegevoegd.";
                resultMessage.style.color = "green";
                form.reset();
            } else {
                resultMessage.textContent = "Fout bij toevoegen.";
                resultMessage.style.color = "red";
            }
        } catch (err) {
            console.error(err);
            resultMessage.textContent = "Netwerkfout of server offline.";
            resultMessage.style.color = "red";
        }
    });

    // GET ALL

    const fetchCryptoButton = document.getElementById('fetchCryptoButton');
    const cryptoPopup = document.getElementById('cryptoPopup');
    const closeCryptoPopup = document.getElementById('closeCryptoPopup');
    const cryptoData = document.getElementById('cryptoData');

    fetchCryptoButton.addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/betaalmethodecryptos');

            if (!response.ok) {
                throw new Error(`HTTP-fout! status: ${response.status}`);
            }

            const cryptos = await response.json();

            if (Array.isArray(cryptos) && cryptos.length > 0) {
                let output = '';
                cryptos.forEach(c => {
                    output += `
                    <strong>Crypto ID:</strong> ${c.id}<br>
                    <strong>Walletadres:</strong> ${c.walletAdres}<br>
                    <strong>Muntsoort:</strong> ${c.muntsoort}<br>
                    <strong>Betaalmethode ID:</strong> ${c.betaalmethode ? c.betaalmethode.id : 'Geen'}<br><br>
                    `;
                });
                cryptoData.innerHTML = output;
            } else {
                cryptoData.textContent = 'Geen crypto betaalmethodes gevonden.';
            }

            cryptoPopup.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij ophalen van crypto betaalmethodes:', error);
            cryptoData.textContent = 'Fout bij ophalen van crypto betaalmethodes.';
            cryptoPopup.classList.remove('hidden');
        }
    });

    closeCryptoPopup.addEventListener('click', () => {
        cryptoPopup.classList.add('hidden');
    });

    // GET BY ID
    const cryptoForm = document.getElementById('getBetaalmethodeCryptoForm');
    const cryptoResultBox = document.getElementById('cryptoInfo');
    const cryptoDetails = document.getElementById('cryptoDetails');

    cryptoForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const idInput = document.getElementById('crypto-id');
        const id = idInput.value.trim();

        if (!id || isNaN(id) || parseInt(id) <= 0) {
            cryptoDetails.innerHTML = "Voer een geldig positief getal in als ID.";
            cryptoResultBox.classList.remove('hidden');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/betaalmethodecryptos/${id}`);

            if (!response.ok) {
                cryptoDetails.innerHTML = `Crypto betaalmethode met ID <b>${id}</b> niet gevonden.`;
                cryptoResultBox.classList.remove('hidden');
                return;
            }

            const cryptoMethode = await response.json();

            if (!cryptoMethode || !cryptoMethode.id || !cryptoMethode.walletAdres || !cryptoMethode.muntsoort) {
                cryptoDetails.innerHTML = "Ongeldige data ontvangen.";
                cryptoResultBox.classList.remove('hidden');
                return;
            }

            cryptoDetails.innerHTML = `
                <b>ID:</b> ${cryptoMethode.id}<br>
                <b>Walletadres:</b> ${cryptoMethode.walletAdres}<br>
                <b>Muntsoort:</b> ${cryptoMethode.muntsoort}
            `;
            cryptoResultBox.classList.remove('hidden');
        } catch (error) {
            console.error('Fout bij ophalen crypto betaalmethode:', error);
            cryptoDetails.innerHTML = "Er is een fout opgetreden bij het ophalen.";
            cryptoResultBox.classList.remove('hidden');
        }
    });

    // UPDATE BETAALMETHODECRYPTO
    document.getElementById('updateBetaalmethodeCryptoForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const idField = document.getElementById('crypto-idd');
        const walletadresField = document.getElementById('crypto-walletadres');
        const muntsoortField = document.getElementById('crypto-muntsoort');
        const resultBox = document.getElementById('updateResultCrypto');

        const id = idField.value.trim();
        const walletadres = walletadresField.value.trim();
        const muntsoort = muntsoortField.value.trim();

        // Validatie
        if (!id || isNaN(id) || parseInt(id) <= 0) {
            resultBox.innerHTML = "Geef een geldig ID (positief getal).";
            resultBox.classList.remove('hidden');
            return;
        }

        if (!walletadres) {
            resultBox.innerHTML = "Voer een geldig walletadres in.";
            resultBox.classList.remove('hidden');
            return;
        }

        if (!muntsoort) {
            resultBox.innerHTML = "Voer een geldige muntsoort in.";
            resultBox.classList.remove('hidden');
            return;
        }

        try {
            // Eerst controleren of het bestaande object bestaat
            const checkResponse = await fetch(`http://localhost:8080/api/betaalmethodecryptos/${id}`);
            if (!checkResponse.ok) {
                resultBox.innerHTML = `Betaalmethode Crypto met ID <b>${id}</b> niet gevonden.`;
                resultBox.classList.remove('hidden');
                return;
            }

            const updatedData = {
                id: parseInt(id),
                walletAdres: walletadres,
                muntsoort: muntsoort
            };

            const response = await fetch(`http://localhost:8080/api/betaalmethodecryptos/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedData)
            });

            if (!response.ok) {
                resultBox.innerHTML = "Fout bij bijwerken. Probeer opnieuw.";
                resultBox.classList.remove('hidden');
                return;
            }

            const result = await response.json();
            resultBox.innerHTML = `Betaalmethode Crypto met ID <b>${result.id}</b> is succesvol bijgewerkt naar:<br>Walletadres: <b>${result.walletAdres}</b><br>Muntsoort: <b>${result.muntsoort}</b>.`;
            resultBox.classList.remove('hidden');
        } catch (error) {
            console.error("Fout:", error);
            resultBox.innerHTML = "Er is een onverwachte fout opgetreden.";
            resultBox.classList.remove('hidden');
        }
    });

    // delete
    const deleteCryptoForm = document.getElementById('deleteBetaalmethodeCryptoForm');
    const cryptoIdInput = document.getElementById('crypto-idp');
    const deleteCryptoMessage = document.getElementById('deleteCryptoMessage');

    if (deleteCryptoForm) {
        deleteCryptoForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            if (cryptoIdInput) {
                const id = cryptoIdInput.value.trim();

                if (id && !isNaN(id) && parseInt(id) > 0) {
                    try {
                        const response = await fetch(`http://localhost:8080/api/betaalmethodecryptos/${id}`, {
                            method: 'DELETE'
                        });

                        if (deleteCryptoMessage) {
                            deleteCryptoMessage.classList.remove('hidden');

                            if (response.status === 204) {
                                deleteCryptoMessage.textContent = `BetaalmethodeCrypto met ID ${id} succesvol verwijderd.`;
                                deleteCryptoMessage.style.color = 'limegreen';
                            }

                            if (response.status === 404) {
                                deleteCryptoMessage.textContent = `BetaalmethodeCrypto met ID ${id} niet gevonden.`;
                                deleteCryptoMessage.style.color = 'orange';
                            }

                            if (response.status !== 204 && response.status !== 404) {
                                deleteCryptoMessage.textContent = "Fout bij verwijderen. Probeer opnieuw.";
                                deleteCryptoMessage.style.color = 'red';
                            }
                        }
                    } catch (error) {
                        if (deleteCryptoMessage) {
                            deleteCryptoMessage.classList.remove('hidden');
                            deleteCryptoMessage.textContent = "Netwerkfout of server onbereikbaar.";
                            deleteCryptoMessage.style.color = 'red';
                        }
                        console.error('Fout bij verwijderen:', error);
                    }
                }

                if (!id || isNaN(id) || parseInt(id) < 1) {
                    if (deleteCryptoMessage) {
                        deleteCryptoMessage.classList.remove('hidden');
                        deleteCryptoMessage.textContent = "Geef een geldig ID op.";
                        deleteCryptoMessage.style.color = 'orangered';
                    }
                }
            }
        });
    }

});
