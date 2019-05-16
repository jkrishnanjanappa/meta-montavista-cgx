include ${@bb.utils.contains("LICENSE_FLAGS_WHITELIST", "xilinx", "", "conf/machine/include/xilinx-no-whitelist.inc", d)}
