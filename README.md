QA Verification – Base Image Update (Mnemonic: SAT)

Verified the following per standard base image update checklist:

Dockerfile (sat-repo): Confirmed FROM reference updated to docker-release.docker.pncint.net/pnc/baseimages/python312-rhel8:3.12-1-0811260919
Golden Images Catalog: Cross-checked version/date (0811260919 → 08/11/2026) against current PNC base images listing to confirm this is the latest approved image
PAC Dashboard (sat-app): Reviewed build sat-00001 (0.1.8-1786550210238-4eaed0d)
Build, provenance, version control, and code scan checks: pass
Open source scan: flagged 255 severes — pending confirmation none are critical
Change request: not found — CR number needs to be attached before Normal/Image Change eligibility shows green

Outstanding items before full sign-off:

Confirm no critical violations in open source scan (255 severes need review)
Attach CR number to enable Normal Change / Image Change eligibility

Questions to ask (re: the 255 severes in open source scan):

Of the 255 flagged severities, how many are rated Critical vs High/Medium/Low?
Are these vulnerabilities coming from the base image itself (python312-rhel8) or from application dependencies (e.g. requirements.txt, cx-Oracle)?
Is there a baseline/exception list — were any of these already known/accepted in a prior scan, or are all 255 new with this build?
Does PAC gating block promotion if critical vulnerabilities are found, or is this scan informational only at this stage?
Is there a target remediation SLA for critical/high findings before this can be promoted past RND/QA?
Who owns triaging this — app team to patch dependencies, or does CaaS need to release an updated base image?
