#!groovy
import hudson.security.*
import jenkins.model.*
import static jenkins.model.Jenkins.instance as jenkins
import jenkins.install.InstallState


def instance = Jenkins.getInstance()


if (!instance.isUseSecurity()) {
    println "--> creating local user 'admin'"

    def hudsonRealm = new HudsonPrivateSecurityRealm(false)
    hudsonRealm.createAccount('{{ jenkins_admin_username }}', '{{ jenkins_admin_password }}')
    instance.setSecurityRealm(hudsonRealm)

    def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
    instance.setAuthorizationStrategy(strategy)
    instance.save()
}

if (!jenkins.installState.isSetupComplete()) {
        InstallState.INITIAL_SETUP_COMPLETED.initializeState()
      }
