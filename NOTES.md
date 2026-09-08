\# Setup notes — bigtable-api



\## Environment

\- Windows laptop

\- Python 3.12.10 (aliased as `py` outside a venv; `python` works once venv is activated)

\- Virtual environment: `venv` (not `.venv`) — create with `py -m venv venv`

\- Activate: `.\\venv\\Scripts\\Activate.ps1`

&#x20; - If blocked by execution policy, run once: `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser`



\## Installed packages

\- fastapi==0.141.1

\- uvicorn\[standard]==0.52.4

\- Installed via: `pip install fastapi "uvicorn\[standard]" --default-timeout=200 --retries 5`

&#x20; (increased timeout needed due to a slow network causing pip read timeouts)



\## Running the API



uvicorn main:app --reload



\- App: http://127.0.0.1:8000

\- Docs: http://127.0.0.1:8000/docs



\## Google Cloud SDK

\- Installed via cloud.google.com/sdk/docs/install

\- Verify: `gcloud --version`

\- Note: after installing new CLI tools, fully restart VS Code — its integrated terminal

&#x20; caches PATH from launch time and won't see new tools until restarted



\## Bigtable emulator (local dev — no real GCP cost)

\- Install component: `gcloud components install bigtable`

\- Install cbt CLI: `gcloud components install cbt`

\- Start emulator (keep this terminal running): `gcloud beta emulators bigtable start`

&#x20; - Runs on 127.0.0.1:8086

\- In a SEPARATE terminal, point tools at the emulator (per-session, must repeat in new tabs):



$env:BIGTABLE\_EMULATOR\_HOST="127.0.0.1:8086"





\## cbt CLI basics (against the emulator)



cbt -project=test-project -instance=test-instance createtable my-table

cbt -project=test-project -instance=test-instance createfamily my-table cf1

cbt -project=test-project -instance=test-instance set my-table row1 cf1:name=Ankit

cbt -project=test-project -instance=test-instance read my-table





\## Known gotchas

\- Terminal must show `(venv)` — reactivate per new terminal tab, doesn't persist automatically

\- Windows Python is `py`, not `python`, outside an activated venv

\- New terminal tabs don't inherit `BIGTABLE\_EMULATOR\_HOST` — must re-export it

Maven installed manually to C:\Tools\apache-maven-3.9.16, PATH entry added, the double-nested-zip gotcha (worth remembering — you'll hit weird zip nesting again someday).

