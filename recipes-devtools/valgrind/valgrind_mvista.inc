PR .= ".3"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://Mask-CPUID-support-in-HWCAP-on-aarch64.patch"

inherit ship-recipe-sources