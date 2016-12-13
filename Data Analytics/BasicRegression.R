require(xlsx)
require(MASS)

setwd("/Users/mazda/OneDrive/SimStudent Project/Studies (where we collect data)/Study VI/Data/Master Wide Data")

# Reading data
#
data = read.xlsx("MasterWide_StudyVI_TestScores_V5.xlsx", sheetName = "MasterWide_StudyVI_Test_Analysi", header = T)
dim(data)
# [1] 376 129

data$Pre_PST_PercentCorrect <- as.numeric(as.character(data$Pre_PST_PercentCorrect))
data$Post_PST_PercentCorrect <- as.numeric(as.character(data$Post_PST_PercentCorrect))

data.CAIPP = subset(data, CAIPP==1)
dim(data.CAIPP)

# Regression for PST

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + xtxqx + qxtxt + tqxq + txqxq + txtq + txtxq + xqtqx + xtqtx + xtqx + xtxtx))
anova(lm)
summary(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 402.6838 < 2.2e-16 ***
# xtxqx                    1 0.1254  0.1254   6.1254 0.0140570 *  
# qxtxt                    1 0.0016  0.0016   0.0804 0.7770739    
# tqxq                     1 0.2339  0.2339  11.4300 0.0008507 ***
# txqxq                    1 0.0014  0.0014   0.0707 0.7905353    
# txtq                     1 0.0041  0.0041   0.1990 0.6559243    
# txtxq                    1 0.0025  0.0025   0.1230 0.7260898    
# xqtqx                    1 0.1756  0.1756   8.5792 0.0037469 ** 
# xtqtx                    1 0.0065  0.0065   0.3170 0.5739425    
# xtqx                     1 0.0011  0.0011   0.0551 0.8146945    
# xtxtx                    1 0.0120  0.0120   0.5883 0.4438575    
# Residuals              227 4.6454  0.0205                       
# ---
#   Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
# > summary(lm)
# 
# Call:
#   lm(formula = Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + 
#        xtxqx + qxtxt + tqxq + txqxq + txtq + txtxq + xqtqx + xtqtx + 
#        xtqx + xtxtx)
# 
# Residuals:
#   Min       1Q   Median       3Q      Max 
# -0.41538 -0.08856 -0.00173  0.07427  0.45857 
# 
# Coefficients:
#   Estimate Std. Error t value Pr(>|t|)    
# (Intercept)             0.102766   0.033990   3.023  0.00279 ** 
# Pre_PST_PercentCorrect  0.799748   0.043899  18.218  < 2e-16 ***
# xtxqx                  -0.008501   0.005792  -1.468  0.14356    
# qxtxt                  -0.003059   0.015556  -0.197  0.84430    
# tqxq                    0.005524   0.011523   0.479  0.63214    
# txqxq                   0.005466   0.011154   0.490  0.62456    
# txtq                    0.001996   0.016585   0.120  0.90429    
# txtxq                  -0.002896   0.018656  -0.155  0.87676    
# xqtqx                   0.017436   0.007720   2.259  0.02486 *  
# xtqtx                  -0.004412   0.007758  -0.569  0.57014    
# xtqx                   -0.001072   0.008640  -0.124  0.90135    
# xtxtx                   0.010418   0.013582   0.767  0.44386    
# ---
#   Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
# 
# Residual standard error: 0.1431 on 227 degrees of freedom
# (18 observations deleted due to missingness)
# Multiple R-squared:  0.6546,	Adjusted R-squared:  0.6379 
# F-statistic: 39.11 on 11 and 227 DF,  p-value: < 2.2e-16

step <- stepAIC(lm, direction="both")
step$anova 

# Stepwise Model Path 
# Analysis of Deviance Table
# 
# Initial Model:
#   Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + xtxqx + qxtxt + 
#   tqxq + txqxq + txtq + txtxq + xqtqx + xtqtx + xtqx + xtxtx
# 
# Final Model:
#   Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + xtxqx + tqxq + 
#   xqtqx
# 
# 
# Step Df     Deviance Resid. Df Resid. Dev       AIC
# 1                               227   4.645442 -917.7979
# 2  - txtq  1 0.0002965445       228   4.645739 -919.7826
# 3  - xtqx  1 0.0001397077       229   4.645879 -921.7754
# 4 - txtxq  1 0.0002075644       230   4.646086 -923.7648
# 5 - qxtxt  1 0.0014666445       231   4.647553 -925.6893
# 6 - txqxq  1 0.0041657685       232   4.651719 -927.4752
# 7 - xtqtx  1 0.0152558107       233   4.666974 -928.6927
# 8 - xtxtx  1 0.0176575663       234   4.684632 -929.7901

lmfinal = lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + xtxqx + tqxq + xqtqx, data=data.CAIPP)
anova(lmfinal)
summary(lmfinal)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 411.6288 < 2.2e-16 ***
# xtxqx                    1 0.1254  0.1254   6.2615 0.0130227 *  
# tqxq                     1 0.2296  0.2296  11.4703 0.0008294 ***
# xqtqx                    1 0.1700  0.1700   8.4913 0.0039144 ** 
# Residuals              234 4.6846  0.0200                       
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

# > summary(lmfinal)
# 
# Call:
#   lm(formula = Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + 
#        xtxqx + tqxq + xqtqx, data = data.CAIPP)
# 
# Residuals:
#   Min       1Q   Median       3Q      Max 
# -0.44606 -0.08524 -0.00003  0.07672  0.43589 
# 
# Coefficients:
#   Estimate Std. Error t value Pr(>|t|)    
# (Intercept)             0.102326   0.032078   3.190  0.00162 ** 
# Pre_PST_PercentCorrect  0.810990   0.042271  19.186  < 2e-16 ***
# xtxqx                  -0.008354   0.003685  -2.267  0.02428 *  
# tqxq                    0.012985   0.004998   2.598  0.00998 ** 
# xqtqx                   0.012624   0.004332   2.914  0.00391 ** 
# ---
# Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
# 
# Residual standard error: 0.1415 on 234 degrees of freedom
# (18 observations deleted due to missingness)
# Multiple R-squared:  0.6517,	Adjusted R-squared:  0.6458 
# F-statistic: 109.5 on 4 and 234 DF,  p-value: < 2.2e-16

# The following model is wrong.  Not the top 10 N-gram for differential sequence, but just the top 10 among Good
lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + qxq + qxqx + txqx + xq + xqx + xqxq + xqxtx	+ xtx + xtxqx))
anova(lm)
summary(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df                      Sum Sq Mean Sq    F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 414.8924 < 2.2e-16 ***
# qx                       1 0.0505  0.0505   2.5418  0.112259    
# qxq                      1 0.0002  0.0002   0.0097  0.921437    
# qxqx                     1 0.3428  0.3428  17.2588 4.621e-05 ***
# txqx                     1 0.2202  0.2202  11.0872  0.001014 ** 
# xq                       1 0.0116  0.0116   0.5840  0.445543    
# xqx                      1 0.0088  0.0088   0.4413  0.507189    
# xqxq                     1 0.0317  0.0317   1.5961  0.207751    
# xqxtx                    1 0.0158  0.0158   0.7950  0.373547    
# xtx                      1 0.0190  0.0190   0.9546  0.329593    
# xtxqx                    1 0.0004  0.0004   0.0178  0.894042    
# Residuals              227 4.5087  0.0199                       

# Stepwise regression
step <- stepAIC(lm, direction="both")
step$anova 

# Stepwise Model Path 
# Analysis of Deviance Table
# 
# Initial Model:
#   Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + qxq + 
#   qxqx + txqx + xq + xqx + xqxq + xqxtx + xtx + xtxqx
# 
# Final Model:
#   Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + qxq + 
#   qxqx + txqx
# 
# 
# Step Df     Deviance Resid. Df Resid. Dev       AIC
# 1                               227   4.508745 -924.9363
# 2 - xtxqx  1 0.0003531473       228   4.509098 -926.9176
# 3    - xq  1 0.0110484847       229   4.520147 -928.3327
# 4   - xqx  1 0.0024647323       230   4.522612 -930.2024
# 5   - xtx  1 0.0183635437       231   4.540975 -931.2339
# 6 - xqxtx  1 0.0194636794       232   4.560439 -932.2117
# 7  - xqxq  1 0.0354763192       233   4.595915 -932.3597


lm = lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + qxq + qxqx + txqx, data = data.CAIPP)
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 417.7816 < 2.2e-16 ***
# qx                       1 0.0505  0.0505   2.5595 0.1109874    
# qxq                      1 0.0002  0.0002   0.0098 0.9211629    
# qxqx                     1 0.3428  0.3428  17.3790 4.319e-05 ***
# txqx                     1 0.2202  0.2202  11.1645 0.0009711 ***
# Residuals              233 4.5959  0.0197                       


lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + qxqx + txqx + xq + xqx + xqxq + xqxtx	+ xtx + xtxqx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 413.4467 < 2.2e-16 ***
# qx                       1 0.0505  0.0505   2.5330 0.1128767    
# qxqx                     1 0.0043  0.0043   0.2167 0.6419756    
# txqx                     1 0.2847  0.2847  14.2824 0.0002009 ***
# xq                       1 0.0892  0.0892   4.4729 0.0355223 *  
# xqx                      1 0.0921  0.0921   4.6228 0.0326031 *  
# xqxq                     1 0.1025  0.1025   5.1428 0.0242794 *  
# xqxtx                    1 0.0130  0.0130   0.6504 0.4208150    
# xtx                      1 0.0286  0.0286   1.4370 0.2318706    
# xtxqx                    1 0.0003  0.0003   0.0144 0.9046665    
# Residuals              228 4.5444  0.0199                       

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + qxqx + txqx + xq + xqx + xqxq + xqxtx	+ xtx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 415.2339 < 2.2e-16 ***
# qx                       1 0.0505  0.0505   2.5439 0.1120998    
# qxqx                     1 0.0043  0.0043   0.2177 0.6412553    
# txqx                     1 0.2847  0.2847  14.3442 0.0001946 ***
# xq                       1 0.0892  0.0892   4.4922 0.0351259 *  
# xqx                      1 0.0921  0.0921   4.6427 0.0322287 *  
# xqxq                     1 0.1025  0.1025   5.1651 0.0239735 *  
# xqxtx                    1 0.0130  0.0130   0.6532 0.4198105    
# xtx                      1 0.0286  0.0286   1.4432 0.2308620    
# Residuals              229 4.5447  0.0198                       

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + txqx + xq + xqx + xqxq + xqxtx + xtx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 414.0272 < 2.2e-16 ***
# qx                       1 0.0505  0.0505   2.5365 0.1126137    
# txqx                     1 0.0553  0.0553   2.7792 0.0968564 .  
# xq                       1 0.2792  0.2792  14.0285 0.0002276 ***
# xqx                      1 0.0552  0.0552   2.7750 0.0971059 .  
# xqxq                     1 0.1639  0.1639   8.2345 0.0044930 ** 
# xqxtx                    1 0.0134  0.0134   0.6729 0.4129012    
# xtx                      1 0.0142  0.0142   0.7127 0.3994426    
# Residuals              230 4.5779  0.0199                       

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + txqx + xq + xqx + xqxq + xtx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 413.3854 < 2.2e-16 ***
# qx                       1 0.0505  0.0505   2.5326 0.1128852    
# txqx                     1 0.0553  0.0553   2.7749 0.0971080 .  
# xq                       1 0.2792  0.2792  14.0068 0.0002299 ***
# xqx                      1 0.0552  0.0552   2.7707 0.0973579 .  
# xqxq                     1 0.1639  0.1639   8.2217 0.0045219 ** 
# xtx                      1 0.0005  0.0005   0.0269 0.8699196    
# Residuals              231 4.6049  0.0199                       

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + qx + txqx + xq + xqx + xqxq))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 415.1267 < 2.2e-16 ***
# qx                       1 0.0505  0.0505   2.5432  0.112128    
# txqx                     1 0.0553  0.0553   2.7866  0.096405 .  
# xq                       1 0.2792  0.2792  14.0658  0.000223 ***
# xqx                      1 0.0552  0.0552   2.7824  0.096654 .  
# xqxq                     1 0.1639  0.1639   8.2563  0.004438 ** 
# Residuals              232 4.6055  0.0199                       

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + txqx + xq + xqx + xqxq))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 410.5885 < 2.2e-16 ***
# txqx                     1 0.1057  0.1057   5.2684 0.0226060 *  
# xq                       1 0.0074  0.0074   0.3707 0.5432309    
# xqx                      1 0.2239  0.2239  11.1544 0.0009761 ***
# xqxq                     1 0.1961  0.1961   9.7720 0.0019971 ** 
# Residuals              233 4.6764  0.0201                       

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + txqx + xqx + xqxq))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value    Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 401.8424 < 2.2e-16 ***
# txqx                     1 0.1057  0.1057   5.1561 0.0240742 *  
# xqx                      1 0.0033  0.0033   0.1600 0.6895156    
# xqxq                     1 0.3019  0.3019  14.7202 0.0001604 ***
# Residuals              234 4.7987  0.0205                       

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + txqx + xqxq))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq F value  Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 381.338 < 2e-16 ***
# txqx                     1 0.1057  0.1057   4.893 0.02793 *  
# xqxq                     1 0.0255  0.0255   1.181 0.27826    
# Residuals              235 5.0784  0.0216                    

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + txqx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value  Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 381.0460 < 2e-16 ***
# txqx                     1 0.1057  0.1057   4.8893 0.02798 *  
# Residuals              236 5.1039  0.0216                     

# . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
# Effect of "t"
# . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + txqx + xqxtx + xtx + xtxqx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value  Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 384.6098 < 2e-16 ***
# txqx                     1 0.1057  0.1057   4.9350 0.02728 *  
# xqxtx                    1 0.0190  0.0190   0.8865 0.34740    
# xtx                      1 0.0731  0.0731   3.4094 0.06609 .  
# xtxqx                    1 0.0195  0.0195   0.9113 0.34075    
# Residuals              233 4.9923  0.0214                     

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + txqx + xqxtx + xtx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value  Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 384.7556 < 2e-16 ***
# txqx                     1 0.1057  0.1057   4.9369 0.02725 *  
# xqxtx                    1 0.0190  0.0190   0.8868 0.34731    
# xtx                      1 0.0731  0.0731   3.4107 0.06604 .  
# Residuals              234 5.0118  0.0214                     

lm <- with(data.CAIPP, lm(Post_PST_PercentCorrect ~ Pre_PST_PercentCorrect + txqx + xtx))
anova(lm)

# Analysis of Variance Table
# 
# Response: Post_PST_PercentCorrect
# Df Sum Sq Mean Sq  F value  Pr(>F)    
# Pre_PST_PercentCorrect   1 8.2407  8.2407 380.5470 < 2e-16 ***
# txqx                     1 0.1057  0.1057   4.8829 0.02809 *  
# xtx                      1 0.0150  0.0150   0.6910 0.40668    
# Residuals              235 5.0889  0.0217                     

