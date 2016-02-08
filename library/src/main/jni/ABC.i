/*
 * Interface file for swig
 */
%module core

%include "cpointer.i"

/* Create some functions for working with "int *" */
%pointer_functions(int, intp);
%pointer_functions(long, longp);
%pointer_functions(int64_t, int64_tp);
%pointer_functions(uint64_t, uint64_tp);
%pointer_functions(double, doublep);

%include typemaps.i
%apply char * { unsigned char * }

%{
#include "ABC.h"
%}

/* Feed SWIG a modified version of the core header, since we don't want
 * any write accessors for `const char *` structure members: */
%include "ABC-const.h"

%pointer_cast(int64_t *, long *, p64_t_to_long_ptr);
%pointer_cast(int64_t *, double *, p64_t_to_double_ptr);
%pointer_cast(int *, unsigned int *, int_to_uint);
%pointer_cast(long *, tABC_TxInfo ***, longp_to_pppTxInfo);
%pointer_cast(long *, tABC_TxInfo **, longp_to_ppTxInfo);
%pointer_cast(long *, tABC_Currency **, longp_to_ppCurrency);
%pointer_cast(long *, tABC_TxDetails **, longp_to_ppTxDetails);
%pointer_cast(long *, tABC_PasswordRule ***, longp_to_pppPasswordRule);
%pointer_cast(long *, tABC_AccountSettings **, longp_to_ppAccountSettings);
%pointer_cast(long *, char **, longp_to_ppChar);
%pointer_cast(long *, char ***, longp_to_pppChar);
%pointer_cast(long *, tABC_QuestionChoices **, longp_to_ppQuestionChoices);
%pointer_cast(long *, unsigned char **, longp_to_unsigned_ppChar);
%pointer_cast(char **, long *, charpp_to_longp);
%pointer_cast(long *, tABC_SpendTarget **, longPtr_to_ppSpendTarget);
