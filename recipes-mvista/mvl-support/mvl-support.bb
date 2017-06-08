# Copyright (C) 2009 MontaVista Software, Inc.
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE="MIT"
SUMMARY="Information for MontaVista support"
DESCRIPTION = "Information for MontaVista support"
DEPENDS = ""
INHIBIT_DEFAULT_DEPS = "1"
PSTAGING_DISABLED = "1"
PR = "2.2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://mvl-supportinfo"
SSTATE_DISABLE = "1"
mvldir = "${sysconfdir}/mvl7"
mvlfiles = "conf/bblayers.conf conf/devrocket.conf conf/drlog.conf conf/local-content.conf conf/local.conf"
do_install[nostamp] = "1"
do_compile[nostamp] = "1"
PACKAGES="${PN}"
python do_install () {
    import shutil, logging

    destdir = d.getVar("D", True)
    mvldir = base_path_join(destdir, d.getVar("mvldir", True))
    bb.utils.mkdirhier(base_path_join(mvldir, "conf"))

    # Store specified configuration files
    for fn in d.getVar("mvlfiles", True).split():
        fromfn = bb.utils.which(d.getVar("BBPATH", True), fn)
        if not fromfn:
            bb.warn("Unable to install %s from BBPATH, skipping." % fn)
            continue

        destfn = base_path_join(mvldir, fn)
        bb.utils.mkdirhier(os.path.dirname(destfn))
        shutil.copyfile(fromfn, destfn)

    # Emit current configuration metadata
    bb.data.update_data(d)
    emitted = open(base_path_join(mvldir, "conf", "emitted.inc"), "w")

    bb.data.emit_env(emitted, d, True)
    bb.build.exec_func("do_install_script", d)
}

do_install_script () {
    install -d ${D}${bindir}
    cat ${WORKDIR}/mvl-supportinfo | \
        sed -e's,@sysconfdir@,${sysconfdir},g' > ${D}${bindir}/mvl-supportinfo
    chmod +x ${D}${bindir}/mvl-supportinfo
    if [ -e ${TOPDIR}/.mvl-content/project-descriptor.xml ] ; then
         cp ${TOPDIR}/.mvl-content/project-descriptor.xml ${D}${mvldir}/conf/
    fi
}
