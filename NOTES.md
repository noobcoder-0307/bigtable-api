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

**Status as of this document (end of Day 1, Sept 8 2026):** Day 1 complete.

- JDK confirmed already installed (Java 22.0.1).
- Maven installed manually (v3.9.16) — required manual zip download since Chocolatey wasn't
  available on this machine. Hit a double-nested-folder extraction issue (zip extracted into
  itself one level deep) — resolved by flattening the folder before pointing PATH at it.
  Same nesting issue recurred when extracting the Spring Initializr project zip — worth
  remembering as a recurring Windows zip-extraction gotcha, not a one-off.
- Spring Boot project scaffolded via start.spring.io (Spring Boot 4.1.1, Java 21 target,
  Maven build, Group: com.ankit, Artifact: bigtable-api). Lives in java-api/ subfolder of
  the existing bigtable-api repo, per the plan.
- Initial generation was missing the Spring Web dependency (only bare spring-boot-starter
  was included) — caught this because the app exited immediately instead of starting an
  embedded server. Fixed by manually adding spring-boot-starter-web to pom.xml.
- Confirmed embedded Tomcat server starts correctly on port 8080.
- Wrote first real endpoint: GET / returns "Hello World", via a separate HelloController
  class using @RestController + @GetMapping — kept out of the main Application class,
  per Spring convention of separating bootstrap from route logic.
- .gitignore updated with Java/Maven entries (target/, *.class) and .vscode/.
- All work committed in per-milestone commits and pushed; working tree clean.

**Next up — Day 2:** More Java fundamentals (classes, objects vs. static, path/query
parameters in Spring — @PathVariable / @RequestParam) mapped against the FastAPI
patterns already known from Days 1–4 of the original Python track.