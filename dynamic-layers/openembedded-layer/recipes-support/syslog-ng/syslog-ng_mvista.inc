FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit multilib_header

do_install_append () {
    oe_multilib_header syslog-ng/syslog-ng-config.h
}

