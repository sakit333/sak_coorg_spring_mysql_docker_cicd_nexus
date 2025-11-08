import org.sonatype.nexus.security.user.UserManager
def userManager = container.lookup(UserManager.class.name)
def adminUser = userManager.getUser("admin")
if (adminUser != null) {
    log.info("🔐 Setting admin password to 'admin'")
    adminUser.setPassword("admin")
    userManager.updateUser(adminUser)
}
else {
    log.error("❌ Admin user not found!")
}