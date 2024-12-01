// app.js
import { ChannelSidebar } from './components/ChannelSidebar.js';
import { ChatArea } from './components/ChatArea.js';
import { MembersSidebar } from './components/MembersSidebar.js';

class App {
    constructor() {
        this.channelSidebar = new ChannelSidebar('channelSidebar');
        this.chatArea = new ChatArea('chatArea');
        this.membersSidebar = new MembersSidebar('membersSidebar');
    }

    init() {
        // 컴포넌트 초기화
        this.channelSidebar.init();
        this.chatArea.init();
        this.membersSidebar.init();

        // 초기 채널 설정
        const initialChannel = this.channelSidebar.getChannelById('1');
        window.dispatchEvent(new CustomEvent('channelChange', {
            detail: initialChannel
        }));
    }
}

// 앱 시작
document.addEventListener('DOMContentLoaded', () => {
    const app = new App();
    app.init();
});