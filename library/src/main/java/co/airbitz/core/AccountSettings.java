/**
 * Copyright (c) 2014, Airbitz Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted provided that
 * the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Redistribution or use of modified source code requires the express written
 *    permission of Airbitz Inc.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the Airbitz Project.
 */

package co.airbitz.core;

import android.util.Log;

import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import co.airbitz.internal.Jni;
import co.airbitz.internal.SWIGTYPE_p_int64_t;
import co.airbitz.internal.SWIGTYPE_p_long;
import co.airbitz.internal.SWIGTYPE_p_p_sABC_AccountSettings;
import co.airbitz.internal.core;
import co.airbitz.internal.tABC_AccountSettings;
import co.airbitz.internal.tABC_CC;
import co.airbitz.internal.tABC_Error;

public class AccountSettings {
    private static String TAG = AirbitzCore.class.getSimpleName();

    private Account mAccount;
    private tABC_AccountSettings mSettings;

    protected AccountSettings(Account account) {
        mAccount = account;
    }

    protected AccountSettings load() throws AirbitzException {
        tABC_Error error = new tABC_Error();

        SWIGTYPE_p_long lp = core.new_longp();
        SWIGTYPE_p_p_sABC_AccountSettings pAccountSettings = core.longp_to_ppAccountSettings(lp);

        core.ABC_LoadAccountSettings(mAccount.getUsername(), mAccount.getPassword(), pAccountSettings, error);
        if (error.getCode() == tABC_CC.ABC_CC_Ok) {
            mSettings = Jni.newAccountSettings(core.longp_value(lp));
            if (mSettings.getCurrencyNum() == 0) {
                setupDefaultCurrency();
            }
        } else {
            throw new AirbitzException(null, error.getCode(), error);
        }
        return this;
    }

    public tABC_AccountSettings settings() {
        return mSettings;
    }

    protected void setupDefaultCurrency() {
        currencyCode(Currencies.instance().defaultCurrency().code);
        try {
            save();
        } catch (AirbitzException e) {
            AirbitzCore.debugLevel(1, "setupDefaultCurrency error:");
        }
    }

    public void save() throws AirbitzException {
        tABC_Error error = new tABC_Error();
        core.ABC_UpdateAccountSettings(mAccount.getUsername(), mAccount.getPassword(), mSettings, error);
        if (error.getCode() != tABC_CC.ABC_CC_Ok) {
            throw new AirbitzException(null, error.getCode(), error);
        }
    }

    public void setFirstName(String value) {
        settings().setSzFirstName(value);
    }

    public String getFirstName() {
        return settings().getSzFirstName();
    }

    public void setLastName(String value) {
        settings().setSzLastName(value);
    }

    public String getLastName() {
        return settings().getSzLastName();
    }

    public void setNickName(String value) {
        settings().setSzNickname(value);
    }

    public String getNickname() {
        return settings().getSzNickname();
    }

    public void setPIN(String value) {
        settings().setSzPIN(value);
    }

    public String getPIN() {
        return settings().getSzPIN();
    }

    public void showNameOnPayments(boolean value) {
        settings().setBNameOnPayments(value);
    }

    public boolean getBNameOnPayments() {
        return settings().getBNameOnPayments();
    }

    public void setSecondsAutoLogout(int value) {
        settings().setSecondsAutoLogout(value);
    }

    public int getSecondsAutoLogout() {
        return settings().getSecondsAutoLogout();
    }

    public void setRecoveryReminderCount(int value) {
        settings().setRecoveryReminderCount(value);
    }

    public int getRecoveryReminderCount() {
        return settings().getRecoveryReminderCount();
    }

    public void setSzLanguage(String value) {
        settings().setSzLanguage(value);
    }

    public String getSzLanguage() {
        return settings().getSzLanguage();
    }

    public void currencyCode(String value) {
        settings().setCurrencyNum(
                Currencies.instance().map(value));
    }

    public String currencyCode() {
        return Currencies.instance().map(settings().getCurrencyNum());
    }

    public void setExchangeRateSource(String value) {
        settings().setSzExchangeRateSource(value);
    }

    public String getExchangeRateSource() {
        return settings().getSzExchangeRateSource();
    }

    public void setBitcoinDenomination(BitcoinDenomination value) {
        settings().setBitcoinDenomination(value.get());
    }

    public BitcoinDenomination getBitcoinDenomination() {
        return new BitcoinDenomination(settings().getBitcoinDenomination());
    }

    public void setBAdvancedFeatures(boolean value) {
        settings().setBAdvancedFeatures(value);
    }

    public boolean getBAdvancedFeatures() {
        return settings().getBAdvancedFeatures();
    }

    public void setSzFullName(String value) {
        settings().setSzFullName(value);
    }

    public String getSzFullName() {
        return settings().getSzFullName();
    }

    public void setBDailySpendLimit(boolean value) {
        settings().setBDailySpendLimit(value);
    }

    public boolean getBDailySpendLimit() {
        return settings().getBDailySpendLimit();
    }

    public void setDailySpendLimitSatoshis(long spendLimit) {
        SWIGTYPE_p_int64_t limit = core.new_int64_tp();
        Jni.set64BitLongAtPtr(Jni.getCPtr(limit), spendLimit);
        settings().setDailySpendLimitSatoshis(limit);
    }

    public long getDailySpendLimitSatoshis() {
        SWIGTYPE_p_int64_t satoshi = settings().getDailySpendLimitSatoshis();
        return Jni.get64BitLongAtPtr(Jni.getCPtr(satoshi));
    }

    public void setSpendRequirePin(boolean value) {
        settings().setBSpendRequirePin(value);
    }

    public boolean getSpendRequirePin() {
        return settings().getBSpendRequirePin();
    }

    public void setSpendRequirePinSatoshis(long spendLimit) {
        SWIGTYPE_p_int64_t limit = core.new_int64_tp();
        Jni.set64BitLongAtPtr(Jni.getCPtr(limit), spendLimit);
        settings().setSpendRequirePinSatoshis(limit);
    }

    public long getSpendRequirePinSatoshis() {
        SWIGTYPE_p_int64_t satoshi = settings().getSpendRequirePinSatoshis();
        return Jni.get64BitLongAtPtr(Jni.getCPtr(satoshi));
    }

    public void setBDisablePINLogin(boolean value) {
        settings().setBDisablePINLogin(value);
    }

    public boolean getBDisablePINLogin() {
        return settings().getBDisablePINLogin();
    }

    public void setPinLoginCount(int value) {
        settings().setPinLoginCount(value);
    }

    public int getPinLoginCount() {
        return settings().getPinLoginCount();
    }

    public void setBDisableFingerprintLogin(boolean value) {
        settings().setBDisableFingerprintLogin(value);
    }

    public boolean getBDisableFingerprintLogin() {
        return settings().getBDisableFingerprintLogin();
    }
}
