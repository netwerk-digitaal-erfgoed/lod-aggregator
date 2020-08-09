#!/bin/bash

# replace invalid Url strings found in the isVisualizedBy field
sed -i "s|_\"|_%22|g" soch-lsh-total.nt
sed -i "s|\"_|%22_|g" soch-lsh-total.nt
sed -i "s|\",_|%22,_|g" soch-lsh-total.nt
sed -i "s|_(\"|_(%22|g" soch-lsh-total.nt
sed -i "s|\")_|%22)_|g" soch-lsh-total.nt
sed -i "s|modell\"robe|modell%22robe|g" soch-lsh-total.nt
sed -i "s|File:\"|File:%22|g" soch-lsh-total.nt
sed -i "s|ning\"._Har|ning%22._Har|g" soch-lsh-total.nt

# fix wrong replacements
# the fixes above created new problems which were repaired the following way:
sed -i "s|%22_.STERMAN|\"_.STERMAN|g" soch-lsh-total.nt
sed -i "s|%22_..jemte|\"_..jemte|g" soch-lsh-total.nt
sed -i "s|%22_STALL|\"_STALL|g" soch-lsh-total.nt
sed -i "s|44_%22 .|44_\" .|g" soch-lsh-total.nt
# let's hope we found them all

# fix invalid url in image URL (replace ' ' for '%20')
# example: ftp://ftp.emuseumplus.lsh.se/web/hires/M_A/M_A01000-01999/M_A01200-01299/A 1247_001247.jpg
sed -i "s|M_A01200-01299/A |M_A01200-01299/A%20|g" soch-lsh-total.nt

# fix invalid url in image URL (replace ' ' for '%20')
# example <ftp://ftp.emuseumplus.lsh.se/web/hires/M_T/M_T05000-05999/M_T05800-05899/lm_t05881 .jpg>
sed -i "s| .jpg|%20.jpg|g" soch-lsh-total.nt




