/*
 * Copyright 2017-2022 The DLedger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openmessaging.storage.dledger.protocol;

import static io.openmessaging.storage.dledger.protocol.VoteResponse.RESULT.UNKNOWN;

public class VoteResponse extends RequestOrResponse {

    public RESULT voteResult = UNKNOWN;

    public VoteResponse() {

    }

    public VoteResponse(VoteRequest request) {
        copyBaseInfo(request);
    }

    public RESULT getVoteResult() {
        return voteResult;
    }

    public void setVoteResult(RESULT voteResult) {
        this.voteResult = voteResult;
    }

    public VoteResponse voteResult(RESULT voteResult) {
        this.voteResult = voteResult;
        return this;
    }

    public VoteResponse term(long term) {
        this.term = term;
        return this;
    }

    public enum RESULT {
        UNKNOWN,
        ACCEPT,
        REJECT_UNKNOWN_LEADER,
        REJECT_UNEXPECTED_LEADER,
        REJECT_EXPIRED_VOTE_TERM, //投票的请求term小于当前服务器的请求
        REJECT_ALREADY_VOTED,  // 已经投票了
        REJECT_ALREADY_HAS_LEADER, //当前状态是FOLLOW，已经有主节点
        REJECT_TERM_NOT_READY, // 当前term过小，等待下轮增大自己的term
        REJECT_TERM_SMALL_THAN_LEDGER, //当前term比日志term要晓
        REJECT_EXPIRED_LEDGER_TERM,
        REJECT_SMALL_LEDGER_END_INDEX,
        REJECT_TAKING_LEADERSHIP;
    }

    public enum ParseResult {
        WAIT_TO_REVOTE,
        REVOTE_IMMEDIATELY,
        PASSED,
        WAIT_TO_VOTE_NEXT;
    }

    @Override
    public String toString() {
        return "VoteResponse{" +
                "group='" + group + '\'' +
                ", remoteId='" + remoteId + '\'' +
                ", localId='" + localId + '\'' +
                ", code=" + code +
                ", leaderId='" + leaderId + '\'' +
                ", term=" + term +
                ", voteResult=" + voteResult +
                '}';
    }
}
