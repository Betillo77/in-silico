package implementacion.hormigas;

import implementacion.hormigas.ModeloHormigasTSP.HiloHormiga;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JRadioButton;

public class Grafico extends Canvas implements MouseListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	public ArrayList <Integer> mejoresActual = new ArrayList <Integer> ();
	public double solucionActual = 0;
	public ArrayList <Ciudad> ciudades;
	public boolean modoEdicion = true;
	public static Grafico este;
	public boolean terminar = false;
	
	// Imagen hormiga en 36-al
	public static String imagenHormiga = 
		"49eyfgr8791nxu0w23tb9mq8b9rcguy41zrhgoknipzso9ddst10txc0p26b" +
		"dbyngqm8eil2dzhjc92mntweyz4u667ocnlqr38uoqaylm5hlzoagwm1ynjm" +
		"i0tsqhrz4ezljzgtu36gshvo7fqdubna03s88har18d8mxlkgw23yvjjseeu" +
		"khvxh5qmnyqptb7fi86b0ib475snvjwyxz56nxr7h9y2sxmebz4dm38dxi58" +
		"aqwfz4iijkcf7f24fijoyf5hdfj4db9bpbghw0u45bcoqrr5y1zzl29gf7z4" +
		"ozn0fnzzwrdhmqs6qr3rrimqx3wfwhb90oug9c0ol7xcrj3ivxmsun3dnwoy" +
		"t9w9seokk1whzw3o6jv0lwn3hxifyf9mrpkopbny83dl1c31uspj8l7l7ceh" +
		"d18pea0tmq73mbm6ql4vmirwqtsg8f1rhqqos59xgelqghf24ke4q3edatb1" +
		"5z6gro2q32aiebzy4wku8kd57dtnt85k0ghep8tknut6q8lombzitpvn6ssb" +
		"3kc5jsfgok2j6a7dfw53w1bf0yf62hr6697jzp983bs3aifb0kttkb21ub6f" +
		"yh3mm0tuiumwzkccne93bpe8kgep7itdi71tozgy947w740k11pswmes7pl4" +
		"51vejc1xfi0ud0rs6nmb2lpyvhoq11qghnsywbmfj5y2wx0wolbnf4qd5ud1" +
		"y568qjblwc9lvac6o4wvhdvodhplb20u7tvcrvh21vxk6104tcqntknkxycq" +
		"zsd4zzzsgh1grwt208bwax3a08w1ldg55pxqatk2yqd4wxauvnoxgpes5vtw" +
		"r86fjb54r5jsz59o8cyv9exejqkv883nrkebzwa9nukjbh2kxur5f950pzao" +
		"9cqqekz4llkupbtc74t90goftx95m52000mz5vblf361uw2gnq5nc0aqjox6" +
		"ddqoma8zrbykmxy6ofsc9hlsit5xqbfgzt1a92doda9nqzpoa3vktu2oj4ws" +
		"ewu58s0x9l8uqsx1p4gumrce30q82t98fsxq669waz5rmlto072ebh7j4jsn" +
		"9nsc198vtkeyos3nedjp2uufdgzcn0x0sgl6s8zy5riac9arcnf76ppjviwd" +
		"hlxav30ool7bgju7d2kk8p9bsa5jzj10zyny8ju1hp6vcpvnoqxu24mw2hty" +
		"q7iowlp2i41r6nko34ncy4wza0a5l82f06kr1xlok5osqqitr22dps0efbr4" +
		"zusfal790vlab8jv6t3mci4d9u1undq18s6h4vi66d9csjpph3oksd00tef3" +
		"76k9vucq4lojoy0olvvn6xf1qyp8uroqadyzocp3ti2rmkuzdwd211vvruzy" +
		"0xdvc6cn5vzimkbsf0a87q7pd8nrn6jpcgp8ragrgzr3fpbheqcl1drp0jgs" +
		"dhppkh48wybn9vndkfiiq91wd4e1zq2sxf0zs5lmxwdt8p9e2zql7ceuf49v" +
		"b3b5pyd7n3cdkfj3r4nqurjspj0xggz9cvgw1mivtf4zf1vxveyf9y6b35a9" +
		"8plgbgq7zw35xwcd4mjfetpzgd0bmxqpomlyza4v1g28ss2osma6chroc6kl" +
		"bj1eb6ualpjojegdn2swmg9r71tg3tx0j94811irs9gmoajdpst83zm6qbzi" +
		"fh9e5m09lat09l2f8gassjjr7m1ik57yhp1e79y1rsl4bvztxtg7sxyulb3f" +
		"4w4zz9r53hk33oigbzhhrasjr7nhifnf6ro13qnc3jka7f4lwrcs37ab0ew7" +
		"vae97ha9yngxtu2ymlxquzbkntlmi1v8cf4oelz16iwckfxouz598q90amz3" +
		"sbnxk3v4oh76c0m6moxri9txt8r9ar6ztqvqrtgwgjeglt9if8hjpcid8nv0" +
		"nq1o5b0uez9u7dgbk1zmhgsjzarhw3il4p1wir9ianqrfzndgpxhy5cn9tsk" +
		"7xco4w8282t71uqrohbmbkkm09vpkv3trd98gs5djx5vtayyxbbxq86296yq" +
		"ah94d1e7aud55tr6jfmo8eu4zc25k9fv323zbkek08ftlhbfbqv74k2yjst8" +
		"zf17m865qyzec8w4beo7h2kryn34b19a3ipm82ufpkcptq2a07dtq6bweq7m" +
		"hv8atvfm9mk0rqf0xhpuanhhs2oo8s4605qdt1eb3t42fjwxwrfh4ued2fmq" +
		"pp49z8wmxrbno31x7dyikct6udphpt61otfkud7j3dqyca5k2us3l7guv0ai" +
		"idqrq44zsg6m890mi5b1q0zkajukvbd8h26seblb5brtvck89yndqs9dlrd5" +
		"gb7h496kbeoo92s8pvlat0wm94fzll6zg8sgsndljl72mth8xqicq072yzj9" +
		"nnl9kzaz722dt81bp5p8ssrbui0a4u769mtgegh084w20crpjm7eyieelz53" +
		"th7xwcj36kbqe6e0wjtz2ewg3zmpwby8qb3f5qzmp1l6jzqzzhpm0tza2jip" +
		"2sdx74e3dodxmnohsp7peevge1spnxm3juobmlc4438bpfce9a6aaxt228cr" +
		"aesl3avw1e4fcskm7n0t0xg82ve4q9s548yfmc2pcl00wzvyf4b8xfibwz19" +
		"wy7qff4yvgknng6izv6cnsbmq4z123m4or2muzuj0fghsy4xsc9fp8mcl2to" +
		"exvbbsqsjojdwt8ueab982i0gt2b94tw1w2wi987ug2bf1646yltaimk8dsj" +
		"le3azf0zfm9e7lftuyvepdax9jmsq83u3f4butvk39k5qatofe94f17d70w4" +
		"kkymewgw3xndxcejtya45yy2e8kce1hii8lp2ob1t1l1fgqjsb2no679s85q" +
		"28qjq8ouyntnj3gvoy154yhuowfd1plwlfhwq54h05f7ns8y2n9wed0dp0ik" +
		"039uy704rwuztv45oa3yfwv5p5mjp3ub13tf1d502zp0cgi1hsf2ifzv4oub" +
		"a6163y42ujw8cj8g0vv48btnjjgignxupzho07775y9ddk7my6w2j4xu2ewg" +
		"70h57wqyz1juvxwpr5wt38pfwchonq3fg99n6pppn49271q1eo0cwafkwq39" +
		"0w0c5eyxncjklq0gbnfjkpwa38prohxec9yj4j2i3qhpn1yjdldkhtwynnj0" +
		"gp780n9z20ggkihj3wciqc1q306k5ihcf43ues3t5rdtzkyqmn5261gx8ozy" +
		"ikoz6guqxal5is06fhgu26tawgqme0vsw6p4ua3vw25urouhdfdcypxe5gfh" +
		"bl515phkqgqgcttz7oma0d0cxxyiz5wfywnjfozf68hfrt3teqve2l92h0nt" +
		"m06zhlyzih85u67e3j5cxfyoteha77k224p1xfwj0ihkwt2o0uqw7i42ze8d" +
		"ba7ta0rag85ac9l69wgwqqnmdzw6k9z4kzg8prdxjmjbhzao63xt5bmiurei" +
		"b4i97byirgda17nksot7areakc6nfmh0qisrp52sshcekc7qbu3vci2sla8p" +
		"twxgikzi6a4mviu1sqomlgwpfn6lu3suthufr18et1d80vtgmnwmbc3cdb5j" +
		"pdp57otf9hwbbtbvllnq8p9as1fevil0508ggmu0058msg17ftq5fbw7wl50" +
		"po0mm8y3sn69lh02dnpwjenq0wn2dphj1r7ysflkkrvfxhok3idko9ce1qoe" +
		"bbyc12i22vqvy0kl43pwveu1iaokh6xx1yw8bs6ywz67qzygw0d3v86r0x2g" +
		"lbiy6vzjec5lemfqoypq21kb4klclrl49778af772szfdcw6g2nnoj3vrjj0" +
		"bnznjiow7z6ar6zy730vzaxrd7txf6oli9zgkw5jybkpb3f89l4l5ox0ck7h" +
		"4ofa1zk91qzhhwm4h8mibcavmrjyhaiibfmzj37ad5icxg3l0zuo1hvra3o6" +
		"pl7qx8ugpgttphmn8egkiqrp5gybk6gn2ye41t7tt0sfu1upnwjte0psymep" +
		"3936tnwvibjs8xmnifbwjrzfiuozccle1yw3by79ow2xn0m27pijdvcx7uo0" +
		"9nw41n14gzag2jec1wls235ic3xdtccuvwam0kci50n4z20c845stwclcb19" +
		"7hl130rcigkgxv36k1nkto3a8zpbu245gxwavjrsgld1k8opfu3w6900dw48" +
		"ejbqoq8e9vh4qub4m0ryvj6lnz5kkjtnb3gzwxb4gs3fmz91av1hedkwtimt" +
		"33m2mul7mm5kvyld5pnauphsq8kt7vwe8cqahj3qmsauh522tkqoqj9108fm" +
		"930ycubt5bo9svobe6g6iov2liv2p757zc5z86tk3ulykt0xtwt9qcsf46qn" +
		"aw36cse202gpqfxeaw6dqn1ib4pcrv53jj2tmvsje27zn4fi05hljd582ol6" +
		"pur4eurx21n6dzwlluh1kt4742nhy8xsefuuifz8dh9r2z0fyz716tftoh7c" +
		"hzyhr3dj2f1voa5ee02nozo1pmojnv1e41u1u8lcf74tc3fcl8vnz8xvaaqn" +
		"1t1ss2qoahw4wlumi0jpnfaguytaix0jxk3d25dk9ghtk2rp4zdgv4487y5z" +
		"3w481h22wfr7t4bc0pyirza83rus3ebzphiwabslcopotrf1dmu6vx7sybnq" +
		"f8zmnj2bdzrs55yjde1zie2m19dobt2dr6zmquljzfypp3ttk9jt32ol9ds3" +
		"bzys9em9hcd481qf9pvjxvpy02kg91i2blm9r9m1no7p1faenma3hs9pc09h" +
		"nxzx2992ura0dro4lsolv8dqdjxlpskpqfjd9s2514ka1583lsjsaqb9x04c" +
		"pldhgrjwbdb0pcja5ertfeohg6sf7u2tjupr5mn6mtux6pjw3jlyaw20k1tg" +
		"3p2sisk1t7mu965uhkwyzoi9q2hbp6x737obegxc8upyx85ywceg51dmwpnz" +
		"bxptlowqdoiejrxuwn4x2du00lrh0ouavk2edxnuu2adseolh1lzfoydajig" +
		"28oc9bsu3xcxgvh2vt0f1635sggohhzr5uai06xwkg2w2equvciums4x9js7" +
		"ialgi9ox3fsuxh6p9xcizcpymxyv5xr0heh6luy6rmdv4cyzvqh9apvu04qc" +
		"5ur41ln79ti4krfiyld5mrg7oyd0lvvby9u0q1xpotj69ht6gpaljl5lmu5b" +
		"n6kit1cqfmmrp2zvf5q25401nm46wi4qtctfzmaxpmzarci9n77wtz2t7xku" +
		"0crc3d3vsmejlszvyffqzz8sn7bqg1o9v8sk3j9of5dl3tt1u3j2251oo8ow" +
		"amkmms1wop3h3ujwk2730khfvo8wqebd1kx93u7lz1l899qv88g09hqp5tb5" +
		"15igkylzduzzaz9a50rrnobltx0321r6y3czmkjniugcn9zsfx64gb91hd7j" +
		"25tdyicm679f63ly1ns22tzvfs70qjzjkco72um4e5lvxzbr7bszl8v1nfgv" +
		"24fpjxxzjymg8o9cipx8e486l3r541ucr1rg61xwx166ht78bl9lk27mxhoa" +
		"gxwpumpv9b7zumk0i6m6xky1nwoeb1jhwtcq6rtxhqarypob3ogsrrnsk4ez" +
		"1tq3mx8zi6cvh4xc40y272ezkshdbg4o6zkafg1nv148hr4nua3kw02yoi0a" +
		"2on06c8u4hl7e6e9tyll8zk87fe9aa1u5hraik3avg849lrbwnc52diomnuw" +
		"fy00gy8ipobt0gt752nzxdob9gvwu197unkmqvjbjj6hugfgj720ie65k9wf" +
		"vryj66wgx9cqe414d9ofp7er4s2q0a4awxfjkmu4xa43366hd4ees0zashpy" +
		"jgs23zr740902dedoinwemy6jq4oziay62vd1xhluevfanmewtgjbcagic7o" +
		"cp9rusx7o3ns2hunr6ci7rpt3i9vyt23wxxocnsfxluztwnv2gdkuwl982bh" +
		"ggztda8tn1g0wdmb5j7nnzow4v127vk0mcq5e15hpc7n134mu9b2px7g1dyb" +
		"m5u0jzmr92wzt2iq2s1zorp0u6ruavelk2ghfo7w2tr16aq46prvx20061d2" +
		"bneslwatkdoqagcz9osvm8s0hkg9yfukseqxmdyq04gyx46dpcdbo5839dcq" +
		"028164xd0vc2usmelic4vlkpb2b8lodt4uq5vo7cynm7c15pjjalo9ncg9cn" +
		"jerca77r7k5jdvgq4ohm04kqanz25diqsue9e0clzxdv74kp44muerf2xu8x" +
		"wu2j3l4tuhid66018dzvg6ubwvzm43hc5e5m1xfirhhrc34f1cc8s71l89le" +
		"syq5yxpscefwcrq4s47hw2xrch1lo68mhf3vg6xk896r89zv52qitd096otv" +
		"rib3hd2a9vglyb7v34rxaz8j0j6qq0opl7t4zodfflf1wj33h4h6gyaqgsj4" +
		"a5lajvv80he776pmzk3dqhv9sr6xnd4v8usgbv76pw8rihh3n5es6nib1331" +
		"kz5rdp8487htbqq3xgnzsvn7zmgg5ga5xckenm5i5lu6emx8bmimp1h0r9o1" +
		"ysxiipsgkoc5hsalxwvh0t8k1vie3bny6embhsak7t9ok0p079373q49vyt3" +
		"lmdx2b6etbzm5j7m1ojqa9211siv54v3z9ncfwphu4wq63h0n43wnz9nk14m" +
		"294pra2p3g54dbhugd3ryrjgwuqiajim08hag42vy59z89utwdkgj75hugob" +
		"hfo5zwpbdcexhhiky1bauhdymejbuyjile4n0b94bamlj8xrfae45ncevt04" +
		"tgvkh17knpxbp6fbgfsii9cdae0ms9yo6khuspxjl3getcwiytcvxbor2l6p" +
		"tqfog6syfan6qpnm5e344r7e97c5cjizcp6bzs7ng8x47ktsodzwaq1zo712" +
		"zc9rjl4hkwm764ijbqrx0dtxt6m6nw2wwan70laqdbulfvjypq9lbal4jc1k" +
		"5bj6fd2pqipexfyl248hfezwwn1i89gqxtwoa01l89lswsrwm45n3tx25gib" +
		"nilhbst4nzgui3scvz5qdf8w094ki95xvgbpw1w030an3igdtaa72g3we3nb" +
		"c40ijtlx2j60mynu31l6gpsz65n603g1xz7l4zfhqwkuq425fp813o81jseq" +
		"rxte5hzxytha4o88bvbsf0qr0v6v31lhnotmc6bmdxep7dtys1sjv4fw2evr" +
		"i3cx6xb36ha0702ew79esaec500evb6rcleo9893v0v5e7hukf7nlkmdoeng" +
		"hy5pb2weu4ja0bafqlt70czt47whg2mfnyu6nu3o2m06wqerwuouj5cl4pv5" +
		"vxwjo077g10idj542b8c1uv26e8u7vrhy87o0jt6y8jvfna1j1i2ltbbd84h" +
		"hfw4u5nr7n1cdblk1mcwe4y18yfphmpylfaxwuhguvc5u355omoxvb1rvrny" +
		"ws0ccijm3myiuc2afppbe4y5dv3vd8xepqffpwb5hfz3pz4sc98qc2phbuwy" +
		"7le5dlbce7emery9amwrhigha8bx30kkyuiupa0mtcnbbgc721vr5ye5lnvh" +
		"0znsv72ulkc9gns0nfomb9be7t8f4jxgyuw1bzhau0e7vvuxz7nci2agnsem" +
		"k6d12eev7pxpxb1sk4yfeizusa8qr8d2hh0hm6vme9h0dzgej40rcm7xztv0" +
		"llze45hx3akcoijgnl02vnebjok7s6uccbtlg93m0b9wpowbzobuy2igzpec" +
		"w5ob5l1cnod9tmtwaziw8rh8lleevzsjxggvrysr0nm6xd38lrmpjy5lcoyx" +
		"gvywonx2zaxtw93gaxk1xx7m040k4y2zxc7olii8d0pk3gss8p6uklm21eap" +
		"9y9fehof6ucu65kq29ddx2nhsbe2mn7vn059tos0udo1a821ifwkd3lw7o10" +
		"hf5kc8ot3vtaj8vvmnv39ujcd5hq2s4ohu806kzn5rr7jvv8p3op8ey5y327" +
		"jbplwxi2g8ots74h8x4ijldtpgn3jb6ckr24pumq7czrnuqr9qkmc39s9ih1" +
		"cei30568lvs0j13qakzer6sb8ovfmtjc0osj1c0qmlr4qgk5hq1vqbjy9vwq" +
		"ou83q8jh6qr0jfndyfmje6oub63s82tmmxwg83frnroq46gizizsk63apaqx" +
		"zniuzjna3dz83iyz7rj409j279dshcjprvbbo014qul01060op136ujjhjty" +
		"m0ekkov90l0dyd6ftc9az852lww8cu9t11xwn9qq5io3xl5ixkmo018en64e" +
		"5xtyx388df4ph1be9lk7vdj2ae0luo4oxym4oe96ppxkmghp9qx9szxaaw3m" +
		"6f958krszezg3fgoa9jx5liihlh423zml77p85ecydx153siy0cqqjt7qocc" +
		"pmz79feumnbk8zj576wp294ypi5fzrldfzkuibg4xq8s0blfl5615u4rdnxk" +
		"atgzx988fqvc8fg6gc2cr0mnotp4ihrzicbhbhvim6dgmo6k2zu1mgv88rms" +
		"iufc5l3s5k2mqyhmgbmecxjuufx2091jld3qq91khzlpvtrf7y729ml6jye5" +
		"ztf1feey8i23cwjfykvbjj8urv5gi3c6oevhnjvaj3p4yyl3ta23risqsqa5" +
		"c36jl6hg28brndn45876jjkm198seja5azuhh4h0ovudkutjtte23qk26yqj" +
		"kvhzb2x61jtjnx1r7y13hxc8jmujfjylylp427p2s5fem98vjk0x20uljmdk" +
		"t8h263jfpee3hiowotk4sttr1pjdfzusff7gw3dutwq13clgbcn60w9ze3yz" +
		"tzg2u2i139o7qcqwey6xxjtsgcgldhwv83cubbknvr1j6u8kyxvvoqe6rz64" +
		"nv7h3vwd2qbq0ihowc752z2o94to2dg5ncscb5t2rj8v8fewzz7y3a2rdgab" +
		"bwq0dt5o1zn80expdp3ty6rcej1hlgwlvs7xixhontyyj91wc6fi2o7jigfd" +
		"17epp26e62zjxibceh1u9ge7pw8i0an6jrxemg891twc57xzaid72onrr2t4" +
		"beluafrmnqfyur4t6iscj23gngmxh9aa4zrd5nm0orywla6lxj5ea9f5la4v" +
		"82m7y9vof27659bas70o5kih2p8s1mc7pvzhsd9nr1sm13ualpch5bfa1h93" +
		"rlob9ekwcgpz0bpd7fhwobnladlhhckul1aj83bk4hhehl47h3rk5sod2vyh" +
		"g9uju5ofwdb1vsfpctyhcheea2ywiqime0kyjvbi0ihwt3g2zzw0kq4g2xfz" +
		"tmybsq0yieald0187v384g7gkyq3qouo8ov3unvhyh0f2kp1hb5x0tuuy70f" +
		"yi8hxd9kykqyrvy06cimws9uhy0tpjzge3vrrc9v9ufkwoik6i3gdn2yn372" +
		"9h519nl7xkkjj8do599jjb0oj8fcxgx9n63z0fze75vhfvd0ernmt3ga0fwz" +
		"cb71et29r4gecku6nnskae80eybn3mvjqowd899436c6niwg5g5gie6nuepc" +
		"o7p8kcsjio0tzx0rx33lfiotz94hcwc9eke3tw3ryjmdretqboxlttjns9d5" +
		"owksy33301ujnrnqhl1xspnfxsvssohur627pqqfaf3wg12ga12g91lowhpe" +
		"g1pf7tac4gnnmvmv32feiy2ulb6eze1t87hdphvr1w2t3spu6gj1irrxnfr0" +
		"sp608em564qpmuvzs1zbnifvypc361pnmp77itwz58whxgtwamq5tag8aex9" +
		"q76yyhtibipg5e500nkclrpq2aewnlqxhw9dtnucux6fgws0sry81oxb2c4c" +
		"n79lsfmfdd7gut2pjz8v1udaoj6owczouoxrrbfptmdm5bulwjbcc7viscac" +
		"pig9moignyoc5hcduwd87swp4byzwwobm6jiqmpxvn76i8xdw5vv8mileo2c" +
		"mfw83jppv5xoh0azofdz07r6cp54w4no41tflon74ekkgy4ggqw9ts21oyi7" +
		"k986g8mdhiuffv09xv29076arg8ksjd1ve6bljsax00adujok19q66d5zmse" +
		"x0hqetmalaob715pqvk3cgfqxo78i0b2gkxi87tabkpmysn3sh8ez8bhs0tm" +
		"3p0qxdlluqzn9q3dfdn3eqgq8499s2pdzwenudxf9uhhrwyn6yum2jrg7y39" +
		"nx61nyl0m9bwb6zyl75119sbg7a6ym1ap49x0p0q7fb8ets7zmhwx20tbxx8" +
		"t9rsmt8vgh5hanc9pqidcu7yl0g7j6mfh2xv9xntt3wv1daaaix1lq8r3r90" +
		"8bhg85jkro109qx749tm9uxt5lz65zevbng8d8uvuyy2f9gulp2xsl9wvysf" +
		"nolmcmg6gm1qgg5wmpc1x8iekvsbpvkxwvu132dx4zs31lpuntuqluwlbrm6" +
		"gscg96sxgagkz43bw7bz0vzpttiq963ss6dsqi2ufg5spkkaqtv3834zsa3x" +
		"e97hov3jo5b5m3cqqumhotoy9i1kaml02amsse3idqcga44icyw16f7sawru" +
		"fao4rh7tqqo2qkl3894eggvbk4vw7dle2i5n77sbvtb52vi77sin8cimodoq" +
		"q2qp700zt1z5uf7xnvkepva4x2rd18o8nqdlqk54hqu4og8rbtwvlhvk9khf" +
		"in2zce10t3spo2u3x4eg293k2tebizmwgjp2pukjbcgekgmyueed4ovaocs9" +
		"ut023mrlx8m62kj5kp6k12nou7w8w25n067ue3wnvdkd41fazcftz41xtrtz" +
		"hpclttchi46aq30ti6kmy57dl0hf64mrx95rcl06cz3ef1otp25hmafh7imj" +
		"5yexdgz0dtjihwx2p54ngkwc2ph2firivye6go2h39ewj2mfv0c2jp0o9pzy" +
		"xau9gnp6s6xqijrlxqqxdochmwtlefnc3h6ivdsmv2pal8r9ayxsj7jqulzk" +
		"rj5ctlg6dvp95ktwjg1qiuo973r4z03140uocb24xmfm3n9fdrdw4kyf2ed0" +
		"hpmcxbqi94sm36cee8b669kqb3f81dkgphof0fgxvba2wmro9cwzfzq1l0ah" +
		"r89v8py2i09z32lqhyuy4nxra5255jfeyowht855bntmetzp7nwwmnyq6e3f" +
		"bk5sltazuatovt7qufdavmtfjsyyq4y9g042jq6uxsh6ek0h0imsufyw9iqr" +
		"aq53w9brfqgarnxmpvacnai4m2ent95zqseacbzil8919iwzxitak60oivnm" +
		"6skvesv9sv7mx7oyjcbbo9g5b119br6rp2glvvpqnwojfa9gvvuiromao767" +
		"sa850k44mm6rdx5z3yu1840pqa7d3r34arnrp4kpjhaz0xby8s45who025zo" +
		"cypgm19efn8ffh6uk34jfxwlfp0uapjopd909n18r18oz0usmc2levz4c1o3" +
		"h0toxra4jiwf9qlzm947mgsulsumf5j14l5q8wnlnjztjxww09qdlagfdcpw" +
		"a1257jy9cqpcc1t32lbtld2o7nox58fxuudu9wluxfy2fimwoinjw67x67dp" +
		"0917blggpc0u19xxj5l30ovaoyghfagport34qysjot129hvxohbyqqqr73t" +
		"3krql1wc7t3jn9lidb3qmdtdd7ylckf1vbsdjdldmxv9qubepob5vybd5d2r" +
		"a6ebd5y4mnlc00k1qzzrcuua2oa2j2xxclm89ju5kbj1ufs5hgdzff8kq3ea" +
		"3oghj1z6n55juzaentbkuf53q63lnc046gjwy3qa0m8969gr2pls5pv4sceq" +
		"j4c8lk1pmiupyqkufshc32yck2bqhbt0556vcucgegp2845lihsitmerhulq" +
		"8wwnwjfmx91ojg32irw0r6baevnlz0pf23u68lbb9diuwe62hulf5u3dmu2t" +
		"oyvwemsgqszayr62e6ck5u1msrub9dw70mfg258ddtdhsytg5104vilfk1ej" +
		"1d1he2xrxjaqomg6gntd9p4z8uu37fsmmgljujha6wvvrhaqk8u0oqmtzfva" +
		"o3qpxj9qodf21akawpq0s6e9n9ra917hnxveiudzug6sy6rsir9lezlwbjp2" +
		"5955qhr7b80b0v70kvrhhm7fdur8qbdw1xo0btkf1zcv6e8ner0hjabcbr7y" +
		"bnkypbphpv7oiah3rnypab7ywhazp9rrmmom30zxpwvcdl47mp9om60jeui3" +
		"lxn8cgg7rn245dwd4n2l8kefh0pn1oy8re1uq4h6uh3h7i0avxzsmqnmmhwo" +
		"ych26e5gv8uk6eue0ken933zeh3tprupofill10pujorji80vn01h52ag2l3" +
		"sv6vi81sxagz6qyb59j3mj581wj8r023xjc72hne48oe5kxmsdh3uegrav8z" +
		"f4j2rhk10e2pqczvrsi1t3exm5m9d15xzvhpzib65akpy2hen8ubqh4vydcy" +
		"h616hf44eje2ftupiwqqh3fuehvu7w6g08lqhbeu1050nsscxoeu8yziqo2l" +
		"5qpfhns9bupci2hr71hlge18pobttd4sy17arm6cqf5scqje0hs7qgbbkqgo" +
		"ddds9o18c4lvgnxhduxa0jui5gip2ftmgjp2niq399p3kzntigcvlz6yxvs6" +
		"uhmbyjdniu3i6jks71eli0cfmm97f7srcfctmdfxf83sifv1clbwk9599ljz" +
		"b1fp37tiuxjt6ufnbd1f6cvbvm1nc2sx4ri95zogti3rpy1auqfbmrs06ko4" +
		"3vix8t3x2riajutccvoqd62070e9zofejwge0zs1pmli2wbrjg7in8ot51sn" +
		"fua9p74u4d53zyot182ui8v7c6syogm0xry49c3os01dbfxvg0tmd2cl8jkl" +
		"t3nid3coheooyjvqp8ycbe1yrkzx59ztxzh4ual1aylabmi2enba8ntx42px" +
		"m4re7wv4t3zp89kvacnudg1e8igpvhlrmnj6kqjqr9vcrzd7cgajmxfmt47d" +
		"5hetqdta8fs9wqyqtjdyidt8e98ul7l4oki39gs13f35sf5agy5vl0yroqp6" +
		"0cft2bchqfu4a0p2jqdri2193sjx3ko4kgpx2nx8w638jnnrq45ttfj2vv0g" +
		"r0nq4wv11fgwi6tv6yqye68itlditdmtjeuxg1wv436rq3dutet8kgsgpyfs" +
		"s0wpg8ll1oazq4brg9r9ovd1mo68gwmy0qh6416umwyv5bq8x2iyzl30ibqy" +
		"dd38dpy0yukng95fmm5g0mcif80dycez51zc2hiwluwzt577jc5lel4hxu66" +
		"1hz5o2unzk2du7lmi8lw8fim7yo6ui3z8ylmmuaw2k8bpsuin29r9g4nou3n" +
		"781dg4r3o8o6zdpdnrn8oefxvxjgu72vj8fv49802pk4jgs00d9xpsl7ry0y" +
		"1uwojdcewzmcnw6d0terdcq2xqx1842ekdc117iobknkmyob6kaybrnkjd41" +
		"odqccu99gne27wh9qeduqb5tee6uuh9jh1px3enzahc8opp5a539yluo9g0t" +
		"3wiwd2plru31wvl7xau4l28upiw8zkjn86tiqc1w8wf5fvikzk83fb46xk89" +
		"1vqsrni929etw6om9o4vc6u3c40e6zm9o33dnvn4258weigwko6rjz2lmvxk" +
		"9r6wu5jfqezjphciy7nqglxojwn2ypfo3a9lkhn7jjak147lfek0arproi6g" +
		"kcreqk5dvb19xrmb06hi3b0ny05wip4os3bauu8etkuvw1vb1ewb6hsi4t51" +
		"ao2uyhhwzkeh60so7g2e4bz21vfxllwx4lm0qcp1o2hvxa3anzwo55far5v5" +
		"croqf14nnyal0zkgml5ftoxlcgwqyv09bsyvnrbyv4q7purn05xu4kbusfiy" +
		"4airxlcykt0f8hl2g5apga3fbyljpso2ej63d6etnbbnehwi3v0fy00oi8aj" +
		"tryrc0tv4x15gnt30kzdf7kdwxvczd2gipvju9pyas6vu8uk4t23h9thqe10" +
		"mgbnnugzgh7kkrhroc3kam9qhc3cukcjhtbu3fpvrknqvyc4fwlyc0fve73v" +
		"t8ccscovseljll882er397iesh4fcp8e3wb0nghsew8xzuaoab3ak05rd0r4" +
		"iwdh3bxgle4md0w6h1vjawivndidm8gn2tpf2ixo1fq2yoq14ggavwlqf8mo" +
		"350iapo7c1rlwd0mmee8gcktb3xc8urzqze2h1334t1djzsd2t72al9xoxhz" +
		"zj81pr0lbt3lafdapjzzvzj537soez87szjxb8derbh3bnkycel3ltcr4u35" +
		"km0aa7iqrnsvj2mghucdijakqe9o2xk5d34p2my5dlv0xqepl7ae6hg5arx1" +
		"1ce6fz6g2085rlj3k5rqhvz6thut3ip6ndodl53lh1n0ml1l5vrvbxa17lsf" +
		"jhfftycjutbvl8rajmqtplalzj2n639j96g2qdvc6035ia9yy83trufts0jb" +
		"3ryjy3ybtowmsbywfj88fsbnu1w32ehulhnvlgch4sjnronzp7f279b71n22" +
		"ykuhgga66rz2w72regcgp2j1z59o6ge4zzgx7x2op2qvn5k2xqy815524bl3" +
		"roe8vafr7hp0mxgmejmhbp44r6efowp346h7xi42rpk18m3qfwt9hrhvl4h4" +
		"d32zqt0xjh8lzyuduovbrxj6omkmax543bgszls12x3ejb714s4ryx7r1uqv" +
		"kbbj0krcd5fmsfpa96uu3cgy62q19cwheyk3zapttjwgipb3irpqj9w4ayq6" +
		"6atik0rsc1wit54gay9v8w4lgjxflgvoasw0xm0ftaag1l6ojt43f3j7pck2" +
		"uqry6tz4xs1q2sshg4sn22cyy0o3mog5uqmebo7tt9itszd8z8xje9mkh81k" +
		"fresxt298nghw8hdpv6wq8omw6ojexplwckoa4qdq2ybhj16wo138lckumio" +
		"kagz5018uvxspegno6crpagzs1wzy6dvdz1jk5eg7ao858pptyna0ygkw9qv" +
		"yho8vw8aldf8w3hsodqxvqfegfp9z4sf0s60bjkhbrhwczzsqhqrvdy1kk2h" +
		"x7a90h9ycmz1renbo3h0lgwlqn64krw5444lypj2l7z6gqo034jma5z9bzqo" +
		"oaqyzziw34dt2y815j0tavytps2r1728kwst86ah6sl01pe3lmae4ugrhh6f" +
		"hsvte5w8fx1cnqw28fghjqo46oqaoap4m7re9q5sog94r9zoe58rjwtkkdgp" +
		"juauorzbicyhl7gohkf1qpl22gqhmmnbwfmn6cyf13uyq86dym2wtzj14geh" +
		"mahu17lzpykkgox5ex59kcuwe2t7dadx4q0fr0csubwyfbo3zmeb24d6wycp" +
		"k3r4i2fkj9hoi491hnxljd3cg87mlbontikibuhqht4fqud2tbnopiz0d15m" +
		"4sbk1t4v5kkl9hqwh9sf70emron1h2j3srub4qprm458d6ii0hkp8t8k2phy" +
		"blt3gbyj1356zxbvju61wfjasmmbnmo9ibkpq3vmk2q1bv96o5wtckj7cbqz" +
		"z1iu2xgivlzoyixn1yo745wf3dfdbw01crhpjr3drgkgx17h066zoi0gn36n" +
		"0kqrge1ojth8hgl41pz7c62ecfk4smgebq1jbh1zm3sfuk42c74s9kmkyk9k" +
		"pbcib6fi9uodjyhsu9zdavceq69lax2v4d00ybu1li4eszhncqexmbbuhtfi" +
		"tf4mgvr5zzy36ygi50sermestgao25n4lxt80if65sz10ure0ozd511bb4gy" +
		"vcautztnfq6yuyf2kf3p048g5dqv275uj2kt55c0mui02ylyidfyg5sk4e3e" +
		"rl0qb5rv7p1erh00nuoqk9m7i9p471ypxnysd5o9tlsl5v1xjgm3urabhpvo" +
		"rwrsswl0xzfxlx2st1dt90sxni5zhkspsnorh40g7hh63c8ncbe7sfsaos3b" +
		"u1bm93zouq4v8rvtr2tkaefpk83zoith9wjji5hrp73888cvbn5vlxqrb58n" +
		"djq2k6nfj2hl2g0l0d62pjyyioq3al8qhr9piausi6rjhwiypis5xtk7carq" +
		"n8ucv3ucb8mus13wj18l01psxy80nace9lv3bmnwhwot538uzyma12l52md0" +
		"1yl6x7s9b4gwcxjf9ii0etc0a1gqimj2kz21uruw1akv4x0bg2q3ba796zud" +
		"lscqv4ugxl5dems417bqxuaoazyumj3swtow5ip4ae3c582qsifk9b7pi9mo" +
		"zoe9xb92to11x5ful9dd1oo86hremw8j0x7zh2e49t8u8tmyosmmi1s5lcut" +
		"32civ3unmnlb2nuubivodh9g04f2vjwicvpj78yj0gt29g2d3t1p6sfh2t7l" +
		"opqu6wdm0qjrc77uyqxgv258bxjmffe1y8nfehza5z28q1fa3gw3vqy8d07h" +
		"eguw7p3g8kyq4pwx5z0w6hr8zotrhdnq2ww52bdocf6mjc5yd8vl8je0t7b8" +
		"m3urid1898q6s15izribt8a6e1skw4q0d6gzmvbpfkpvygt0zni0w6oz8hf0" +
		"bdz0n4mlpmbslb4evfe5u359g0yncvieu3sfcjwl8pyn38mzlr85rx2vepu6" +
		"9xx1qymgt929rnz0uwst6vd58qqynofwjwvrwjljt6acivzfvtf1hf1g4bic" +
		"i8bb0iu3d2irpzeykwktflmr4azk8lb2bxzl3plzmb1s167l5zmnhn1qiwp0" +
		"tfm492quje49p745137fqn2t2eq7cp69cwoj1ph2a5yhreem5irdrk7xte6q" +
		"j57u2ekv1x7ipxtb6vjpnes1x5bc5h5j5qwq83pt5tpsxk8d6b7abgrtzbye" +
		"mdnvpu7s3vm5495450e78f4gka5ej9aaw0xdgpj975tjv0ku1ib4l9zly9ac" +
		"nj53ic63f8p7wqem3bs7yi7163573mt96bd671i5mcx3ipwdngnio4a00pt8" +
		"i1arr4tqyrp1r95x143bnqcflfcj8if2plw0omtjqif1hejvfkcr77165pei" +
		"0mp6ifkwmi1o7i5ppn7nwwk37p8vq147hq5gin7166ay3p73c0d9gd1sfx1x" +
		"rqvwniekqgji2uzrtn2jngrtrm2tcdb8k9fio4ukrywhhnrlpp5ttk3oj6mh" +
		"1adi3481bkwiskuivc7oo8fyjs3pksox2punna477cl3fefesp0h0zyjzsuk" +
		"t2f47aw5hhn3zaq5tcxtg9t7ku2ht5movddg2muv4ydcsdk1ytl8w2ghf2bb" +
		"btig2xxr4mhja5dr2rohnk380usc7d7r0r5u8673rmnm23mz2dmymiugs6rt" +
		"hchugmsmzuj1frt399tej1phxejfo8omo92a8lxfkwou8d2iwtfhixmv48ae" +
		"9f6olbtk0bnyyjdhka844lwt65li4ilnubvxoosnt9ys2vrm503oongjmqgg" +
		"kajetaoh73atv2q3vi4lhgelmra7eloxl7oy5uueqrvv4bmi64mgisvlztgs" +
		"sigbyqzl2leqd2yjvc1xs0bm3xp8qqmq2g3p59vsjrjutqfv58hdz0dxloym" +
		"kqglmfm12qpya7oyh0vx38ekhpzp08vr3bnhmfipsz8hw9f5cvm9c2k5jv86" +
		"p086xoedi729v23xovptwb11k489bndnvqz80t27xkkpfghwzsmfuniw15kx" +
		"y2jlz0wdrttxbxyu4mrdca45e8lcc8fczx5v129j8cimpoyved1q4ufsajkn" +
		"9l6hxe8zeenstsyblq9g6p2z3aixk0emrm3m6915wux26ckg8iiwxk5o5a94" +
		"7gn8yl4b5dbfwb93q6jmnuy4qc0rk59bd1efjbinines0fp2w0zadegzaec6" +
		"55f2lwboc5trz8h85id4nxfbe0kylpsmabu1541pl4s7b6eprchrr24p59l4" +
		"1l0eozlmjepdwglaien3axkn8g6uedjdurca6ew5f2nnl7d2we3r2umcbxzq" +
		"u81tjzlfwrz7kvjgrnim7hehp91a9it6otzau65yj33oje784fsxjxeb3n0d" +
		"7kjjoqfpyzwjn975qyou5brfybmvbxryy9c3qp658l78941u8l1l3q06y2si" +
		"qaswuw08jw5twff337030w5gjll6tl3iv34hr89eemw2pstvvgcnj66vwlm6" +
		"gnw2hmxjs41s2sy7zcy9bpj793hc5be0wepnt8qyoosocy1979y9kcwcgfe2" +
		"pbcc39ieq0mw9pn6hmztsxroq68ln4k5lw4f4vqcmy5y5scenw2x7mp40obr" +
		"lcjbm89by9eji0uc27maohg3q9ucx7yvq7hun8w2itltebh4iirz9z2vie1n" +
		"86vx6aifbya5gg58t839hol2f36aq5l46352uzw8ceeqiucbxqf1xx7zovst" +
		"jo0js90vrxzw20x2qpov8ngd0fznaz5giv1cjxrzuqdso389f5cpix10tgum" +
		"wykwcg8dk3vut23m8s4fmex72k9t2n2jt3o9oxsytw8931kz1g95yyntak8p" +
		"3pfc2sbktjlad0sjhj9minex8117io0a76vlbnmjtpnom9223vhnks3vrm0t" +
		"uc6wz3w6bm2xjou7u7yqkjmfstju214swuz58ix5o38l51trnf4djqhpa2oj" +
		"vmeembo4yjeyb4q1iqpnlkxdgonxfda1xq8phv9tkfgmkjj25n3bfcd5h0z9" +
		"5r6j3hte5q1nd21kojnjl1t3gb3vbsuromzugfv74ci834nvp81jxmynva36" +
		"5lyf0b2w3pyvk0epvorl5jd6xhyzrcx1zelkvx1ujqpwdyiemqj6cb6a005f" +
		"9lygbvjwry1w7xjt25iin0a6ak57sex33i6bbm15vp8fhhtlfv333qwmn3ds" +
		"htsskj0izn6yhyx9268exde2bwiylfyazm7esidgbm4vi8cfxcauo1zp0om2" +
		"7drxnqwgbz7bu6ud7mpvjhcevhcas6wckcto8ik7k6fpjv4s6f6zlv2pcx1v" +
		"dsyfdxly5xn3dwq34aleom9yny05724e9sn7uwgm4sosa8r8jqhdkaznnle2" +
		"dr72f2td51lq5p9772zdpilgdeoch5a98nf79go2jxoodl161v2qdu2mirnp" +
		"tpohg6kterxy3hjt8ox41c8kpya9xrb0tmjqvmbiyg4pgj604o9oq8y38yfm" +
		"k5t025nu0702huz2q22iwdb86fhnzp0mmy0r2jeywpd3q0epy2mh1v2ll0xf" +
		"0bcj9wodf2ynsre2qrvpb7wx5rkvebmovx2aamyxq5t7nf637diuo4tg5a3e" +
		"d8c8g2460df9k1eqafb7f2uy433nztsdkmbnv8p3nveraz6n4fw1mbzxxi3q" +
		"qiwlu6zvyzf7ulpj07xqwx18tl5jgxwn08n0aitojxrenfvgvu2ql7j8vp6q" +
		"j5n584nxhdkerv20oke5hx36kny554bvi3yz8z1krb4o87vv3djv0e79hgfe" +
		"0xk7foy0l6ryflb11vbk07wgx9dta0hq9940yjoo8dteyvkmbd42i8v36gjn" +
		"pobw3ga1vtgsvwf051h68v5v3t7odui7467zazf5c1xslf4zmmvxtzdpdhrv" +
		"kuw2uc2xynuivmz8ytc74387958ci0hpjwxlr0g56vljixll7nztaq3lpw02" +
		"9twq4594w67m3p4f9jqhincmad820r9l1f210g7jg2bgtcv63ipvpkzjwhon" +
		"pwbi0q6jbnjwj07zgsfjbj4lvmed62zb88uz027vw6919qbawukwn04f685b" +
		"zmxbrftd02vq4zc8hm6lmwr6sd8pjboq0lmjmez9g4vcfpn1mpm9pbj6xk6s" +
		"6g0cyudy4jop0ndy35rlkqiq1700i7ce9v2hh4vxf812vod7gdlp0n7ysttx" +
		"bvi94cqwws2nufahp5t0mttqcocqv8oi5105r7u0i2blxorc6ueo8se3j7r6" +
		"mw5vuivrtomkgc4etgz0scnmmzrbf0qhg8uzzkvf9yz6wjnbf01dspcjd3yf" +
		"8997qy36gmt9jlgsbcngjjo0evt3g6d9n23n1raq62e1bzaeeu70iefp35ox" +
		"xuv5izvkys4orhrp8acvsf2b0whd5y186ma9yx32x0yxwaip8dfkpntd4c3v" +
		"oel65xf0lc4irkw35q82tlplf4avsz85ott1ykfzc8c366hebzg2t2hdrvqj" +
		"0v2gr4fmdoyhpk4auikj0qtc4h6mz3moluchqvokequeejh91l4wq18lack4" +
		"owi56a861tnufgwpj5tgvqvx8rfh7uz06u8exgoyot26ujx8aidndwx6e0e8" +
		"n1riowjvsbhvmdd4l52grq7ehg3sn89ipirzv2t8stigmf4sg9um2c2rdhvo" +
		"eaznc3ijkn4zoebwuliaa6kgfz410tmlagnoin1k353c1lfw9qzcff8ntv3u" +
		"j2fe6d82yfb0ps5acsbnk31h6q7vys2fxiit2sv2fh3jnnjwh3hbm4557lfy" +
		"96s6z1zbrl6dcancr7arz4i72jd9tighpi00aqjy03f1vtj7h20c73f7fmxy" +
		"g4m3fj5rbz588ygudtaddnlbqxi46y3yom0ue3dt84j88vuyql11cciin1o1" +
		"kqrlksu5b33tof6teo36fzzuxvxotu0tn2q0emwxma3itw65fqcjxvjxaeuv" +
		"t0diw70yf23u0aa2rnqxnmt2il3g7n6tgb7hwuszzh1n8yruj8ztpk9vcejg" +
		"0unlono0i3tabumi7asnv4gtinhp5t5oog35abzj67kgegf72q86ivzyzdpk" +
		"tzfuno4pleuduib9eciokku2go7k7pofgoh3xvq9q84iqamhkuty10ubzibe" +
		"ll7n4vgjjwo62qf17cmblh1yf3sdoyq7wxgw4rxtimcfnsgy8pn5l6n81mfd" +
		"snbcjvw49az66xi0wyq8l3wwbuyrrrfn1r2i5g0zyg1vx8uiq3oopdgqipjg" +
		"awjk6pqj65o789dmwm7d3czi1tgvon50scskgk6v4f97tgx2rtpd3niwaooz" +
		"efocqp47atob2spsmtbsttfol1rc9opeutne22qksguy73clzggfchi8uwvb" +
		"t2rp74mmz4hkgc1ip70qy7se3o1y1tyohojvyly1by9m0r04mnf50vc8j7h8" +
		"zj2nh60q0mxnilybtghrj3hidwocd5ji0wit8o8l2c88hqnrk75mf45xnqu2" +
		"vgoaw3ybc3ln5k8bv7vimz73babgrmgri1e5y3v1as1gh9hb6r5ultqin0na" +
		"loky3eazpejctzrkvrhnwdjj5pyma8oyeeu4eq0662d7hsevkkenu4od2m3y" +
		"210zr77yadv2yiutrfgxlmeg513jtixdonw5mjiozo6p0bplkgh04gnqosy6" +
		"nnok4yerri39kjgd7tiu4gqx8qiuw0hnutz7n56fmsou76tt4nhjqhc7n28u" +
		"698kmtmtqxo8yxmqzed86ipkr3xcnwjxv5lwpb9it6uu435yje05rgwf57qh" +
		"97eul4x08owxgdhsazp9uh1vye6uwuw07scno3miprxw7n7w3yit74ldfqxf" +
		"qh79c2x9bqczkj2ihy07ijrf8mqx72v3pv27iiqhnd6v5gjkmbg8ab1gg2i9" +
		"rji5z8up8myzr3b583uhrcne8ku7pt36eg8izc8kqy4m2r80migdte2j5h8n" +
		"x3fxqwqc81ubruw8dxfuluskqn44kbalsy30zptoxb8ccgjro1ehbp7zizcq" +
		"i3rcidwqjq4nl7ng7jxfz3kvhbn9xmw9o5kr3r50vh41srf4fs7qlp3zg623" +
		"qeart5s0x0zusi4xfz50v5fx80kyh7u3fgq5ef31nqn6ym6q4war1k7tw4x0" +
		"erhzxezkm5wptrcam7tkk8i3z280seqscixw9dnifpf71b2esvow2fus5162" +
		"mbky561hwkl1hzfdug78vntb2jxx8z9pk4bja75ldje0k0a25r7bwk22amz5" +
		"8r7u8fyvv5wyohvy1lkaojf76un7cwrhlllnzi80z7h9qch77i10s43tckqo" +
		"2nl5ajvxquoe90edcmyp0h1si1x8lbf0mh7orpsweh8v2csz6mr5wv9y1zcn" +
		"7v7jwbi70sf05hye2y5wbk2g85olaxle9x61ogjlwafoeihk8i5cd0iklfz9" +
		"qbp1k2vw8e5v3hgb6107pn8emrh0ctk8pxsze5p0de2rw4qpoz6b35u1jpy3" +
		"tn7mqcypb0tbdnjd2ga2fyv16evjmepy80h7glfozzi6vucn6i4i5pa76682" +
		"rpkhaf3keqnxux3v8yn6xjo76t452zmrq7vlpp1vgwxqnxugjxk8ylbeqwbw" +
		"szunpd6xezx3znz8gkf5r4p2ayxwu98ta43pgg5upsrqkwnrmtwwjv35kf3j" +
		"ymv3t4btuojd2dm2jmkpozvwkdufnpvu4kt81fql1aa2xl3aom7jm3gjydy9" +
		"cbs5ew48tl43630jo4bcocbx8kwmelry6t59wljt4dk2k2nijvtik6l5tj82" +
		"s6vwgibby2y541fsbmn6bvcago3psibns1fyucorpkr452oasedw6mo8ir5k" +
		"igytvvks2duw8bfa9ao65urpekjbcv257h5ykhf7tnh73l1cfclf4upqejfm" +
		"t6p5x82kb8m3pcuk3s1p327jfilmljbu9qlpi7q8fboxy81yz3gw4umgvrp6" +
		"nlr3zi59hgnh0le35yqgw6loco7f5trzs4tjd9vzwibbw3c670wlzrsldvp8" +
		"0htkulsogs09fg95a692s5pym44ycnttm9wnqao04kp8s9ycfips7ql6pjgc" +
		"enlnng49j963rlnb7g1h06hp15t708mf0ft6ac0xbbahtslly7eoetzir2nk" +
		"2d2h0ovulcpgx09xy8x0tut04l8xi1yirvtt4k20mcqjam7zjlgf1belijwn" +
		"o6h4pnod4vz7yhgzrsm4pxmqeb00b5bzncdbt9elimc516nsjbv6pjv5qir6" +
		"mfqzcp8xn7cxr1e5lfzhebwmzdyuhaz6eeyb25ciir6bo1xo5wzd51zg8697" +
		"wz7rs6o3o83q2ee59vhoutgx6oe0wcuom54iukhsxb9guvn7i2216thvyd3b" +
		"tqgaah5imu7htl47wg1v6aoy717b0q9v56pgm7b987kucwi82r2hlo2h4hn6" +
		"7ofmgr5y3i17ihivqrh8nfsisdkaph0hif1ca4vm583hjt6hvjfsm8fuav4x" +
		"b7mprlykun0f35w27i77hhuszw1n1erd019l9e5qizafbzyf72qq69cbsurv" +
		"3vxe5mzemm3jeoa58jlugcnxaoi2ogyj2sbtom5bok1rj8vut73ek5xfnl49" +
		"ffbpc15lrgki1zm4ww9otto96v41rws9zytnkzcy5fml3j1r957p2xybpage" +
		"jc2oa8hm3upaewa6nu261hg69oh90hbnbo9vsf7io6s2e49kgca4gf49338z" +
		"g8hf18awu2n881m6yx9ua1go225csvs912dmcwak48fju11xqimya08tsk2p" +
		"p5ysoq9x1ch2rjekg7rn45hbov5cz7222ehhqqvd0br6v7y0hampxplktd7k" +
		"1plv5dbge7s9yi5te5cz9my6gbzou0a2wz4m6g5q75qavvhwkzgbs8gzt3kq" +
		"j67rvfg9tlrbdcigohi3ywo0puuiv0bzkcqpc9vq2ee54csyr4ihs8orn82t" +
		"quo1exod4fjn3ohaqlts0tat6rqtz84qops0p1adg3w0xubzt66gzc5599bz" +
		"qk1wx3st0droy0knlsip3l35eqxq6hdwor9g0atwfdejt2yjytj38ttgzlay" +
		"1ytbnucb6hqeokyhkhbk2hpdl62ygiblobh9zk9qte1jvc9lcuzmhy089zqj" +
		"nrieyetj178i655pckom35iyygxmy1gnibi0ld2vou8oiwq16moa836qlwo9" +
		"wwm1fylf03pzqooq4gqc58feq8xher4o28hoe2lp8floxby6vhm7z009ti8n" +
		"rdrgu3g9zhnnbgk5tmhlu0wfir3hjid7m0wyzeh40tl7dpmb6dvbygsdlxir" +
		"cw0g6eidsrmjpe1ook63ynximbn82mu8mt7f11k4jjmp80p6evf08wzgpn68" +
		"mjc0c9ej0jsff0y6gq0kl5fb1qikw72rprw6xksl93b0v2wvuc27ab5z12k6" +
		"4dps6tovbk3cm6tsp6tywzpbdwin5x6i1vt55e8i0lrk714bsms73zpncs6q" +
		"4rcux8i5iquekzvmmdmw8ejx03978r3ydcnqp1rshji7p2rs5m0n3444o9dn" +
		"oqocxy6e6wses4upspkvq3ow02doa1mwzdunxux0u6mvq6da3t6uy6wrekz5" +
		"1pp2dalbkoosizt0k4pmzhrntdlzwga75ha23asc4s72ti2f5cwqgom0r844" +
		"3rwp9ewvp54h7gk3oypwsk41gi7izq2kjgpm2j6sgj37iqz4le15f27fyr84" +
		"ogib7pyctjl1scr34fv7x5yfzxd651iv64m0nkerk8kbiff9s41op5tguvwy" +
		"q2kpnlpmu5fpzznpullo8kpwhvcn97ck1oxjafqkdv8ddhcf2p7l2ikfebjz" +
		"ahbodspien96mgmestrdl5pvct2vrb36ujmkrt97a188nr3cu9eakwoh2j2m" +
		"pxoibd6ouvgwfjr8zjlwv30rmoc2fsxjik5ni09lnjxr52t9a2e37o4tgp4k" +
		"u3ycyjjc5kzb8ptbr7u9ntn56qy8e6wepolmb0nohgdorqp7n68ff6jeuzkm" +
		"q8pugks872w7icn1xe4iw5onktix3aymsoqkgnkhkx4g0wt1ihrlh044gyd7" +
		"37lq2ehxxi9cjks0fxnt54phl5zlkzf8in3otphg8f2xxvddh9skudh9aymc" +
		"1sgjyptidze6ttfn3xeyzts0afb02z5ucx0wtyn0j8qyxsbbvq1dygx5hty8" +
		"zg26ldiizlj4a53z0xcsbvf0mheodnedlf8dydx7gzsu0icda2szalw1v84l" +
		"kumcgqe43eito8qfpmi0vsq6nd9e92pyay7yvqyhfuaqv4j3tvd4qcbvln9p" +
		"yjgyx8x6fbl0knzrzid62eehh86ups09tsnt6nbdd43790slv96mfs0qwzds" +
		"k8sp5lbd8xkidy2zg7zjyj1mw5v4l327whqw5eke78unxpm4mgnv6d56adhf" +
		"ac3htdl09ms18w2dda83scnb1yn39po29hfn1hhauppyganlpdv3lbja2656" +
		"336q6dshb7x10034zgmeqporlijs06djp7hd9altfq47w6x8fdexag06zqni" +
		"5y9lphvrkakr6c38cs1vy9dpyaytvmeg70y6lpaqmwlxeohogxaq11067mw3" +
		"g76uude1rgp8spe5pgu54p6cct92yanm2bj1j3mpqrr0956b7aw7hsfauxn2" +
		"bee7boxb2fbz95l6zpql6i2tyk7kh69wbcn2vib3fk41zlm4p99m7hhk0uzd" +
		"x4wbmqxo77d8qy7m194u03mkci2ds6fnw9410ykebjjs4e0s7sf6x7a809z7" +
		"qpttrie2yw44hjdg2sreia377qvkib09lpkte1vo1vr23o2ps30phlc51fco" +
		"i3zrbksqbglrz91zv26c6q0ssiba78pw5s79kfox5vkbn4hwhulz23f66kty" +
		"vtlrkzg777totp2x0u1d5vsm506npobym4rqhiqsypuw41vupjcrxab3a06y" +
		"4v107okfr9hxkfj2zhruv1f9t1ut5omeett14chcb1kztk30phrez12d7ds2" +
		"d7e7ntge7aa46s555ou21ykexmv7qz302v96ewav8ckdo398whdi977822cr" +
		"8ab2qcm2tplwvrtyx5i8qw8uss29j7p55lo42ws54hvog7xqbbqt7or2e331" +
		"6opqm0hv37bxhba7hgo7t3s95nvv88jo1g5zjne3vnztdvbjxf58hk658udw" +
		"x0kb2myit3nls0gmilk3n8ib00iw6f6dd05a037wrj7cflyk70e499cm1eml" +
		"7llh8397qo1qno65xm7s4d1vjkm31mebfgvrslxsrtpl854haq3lvjdffwh0" +
		"rn0d5axeoqy3xpmk0ntehm3y6ngv34wfcma5cvk9wwcilo9fjfrreput8dq5" +
		"hrtgldqi3s858pyekougc22r3xd28m1phm3tmqip79edq1f90znyp3rg759e" +
		"lq441mkdzwt4lj7219x82vfdpokqdlaieoi1x8ql1v3qia3qyifw9ug58npc" +
		"tlkvaezc4pqj4h2pfm3ad4nz8pm60web50jku8n3epf9h58gl39agl2qmqp2" +
		"dp85f18qulb3y0fvty9tf0v57ou0p6dmtldnd4oi24zo7xyxvjm7akivmaxp" +
		"hq56pt1tke01yhjyjwlnz23jij155j0y47700lqpyl9nl0jkn3bk7lxi7tl0" +
		"0trfhpqpwmhkfp349bjp6c5ftk6bdnz330j4s7v10afkp2svmbyoe46md7fc" +
		"q2ihw59co44xxksbw9xaxka51hvarab2bv9t79u782sn2m608n5srqx5zmdm" +
		"fuyv42zrx6iu1ujrm98qpethbp9a9hl0wpuaqqg0iys2i5rib704arzzuvnb" +
		"2v533hdhm43koblql2qedl19tp6jv3aih0vn3pqs4mvwej0423f4heloprqf" +
		"nmvanukpjqld9gylo77ygydszu7ixgkkjmubzuqt86p884nv2nwez749cz68" +
		"vk4otiqelnw4kxuikjkbmp19jj660e5oja70x2h9jolvtpzua9l7id5juzw9" +
		"mvrbl4e6216iamxn03249focdurm70rrqhz64z6aszd25wsykz9nqixdouoa" +
		"lrdp9ea67j7bicb6q52ialhwswizvr7d32iowdhcwru3gy15h16dwcvvd5p3" +
		"2kmrnl5z3odoptaxlzccnwh0oodfnzftd4b05h7m6g5jvjwwb5ek09dpyoko" +
		"947fm8xvux0f26ex65v2jst6j9l3y2a8df21aimnomvmpgounu1iiywfzzc2" +
		"t0vwq4fgb2lfd6b8h82fk4jslhbkygelp67h1wpje9xwuirq9jrykz6cmph6" +
		"12px5xdjmvv267kjnn5upiypgs5nzhlsmyqcu1bm3a0przl0rqlva3m4yk4i" +
		"e8lvzadoaejhjfcxl2n4ykuqwmq4v3hg80ozd8otzusko2s2mzwxn8l22a4r" +
		"fuhzov1i3j4pfsrnjwjmyzz2zfq5ynx4kk2lp7jrfdp3bbdt3chrjqpv8e5e" +
		"3grtmw5n4eeiq38n6v0soygwkj58uid1yugemcta3gsey4tkzhp0nllf6r7j" +
		"auf9lrb3wiombf3j3hx6w4kiadgcwvqusb9sa3ecz5tgy2hs095b7gckxz92" +
		"mygpgs8sx45cr6tmqo6pjkxzp5yc17q8rgv4ma9ees9gj7orbkf647it23ws" +
		"1zqpq8d5sdil5vfqzdrueeb1k8oxd0vlwe20xt3yw8i7v5xzb091d9ngbz2e" +
		"zrcuup9unfr4tpq6c61dzy1ivh4wkhxacd6760gtigpj4ea55tnebux2tve8" +
		"aiss2knp617j1pcbig6wx661innpivv5p41garpeng3j2yaevb4ci6zc958p" +
		"3efs870sk65vulzfnmvqyqx5h6lrcf0bq2f9lzhepgkkpwslaziyog8cj8l0" +
		"xl9tb2ehfgbh2nym4kr2cj8v7ha98k2c0bgnxa1filfc081ab269qvw8v1kw" +
		"fjzq2lt67sd32383la4fg9jhazbdt7l2s490exj162gdmd82l71wc06sdcvg" +
		"065qnkc8v51z8bhfjbuwqj6vc2p0fe76e6bvl2artnjnzx1o2q65kjf62gf0" +
		"p3pmc6q4chglvd8dlye1iap9k54h21r5j9pjemkkn6pnce8rxay8xpfjnuk7" +
		"kgodx8eh0tr9dm1zuiz78cpf2cpn2yqjyj0bqc6713zh8jisvkbx6jeoesmu" +
		"0v3k1vwe03j0tikqb01zndm20jxnmkyv625g38iaxmu9t7vjhwhy2v84vtrw" +
		"6nc8223yaea9i25hxqlnxs897ex3i53v80h42dl7spugnguyqndh18xdq6d5" +
		"804zouzuvglo9m37xsh0zlu3emd9euiphb556bryjrycyjdmo8t1pltrjjwl" +
		"srapsq0wp7eia6geqg82aqntn3kc19wli6kj5gbll869slbmnnys73akkbke" +
		"jly4ewkwfzrz7akpix1rfk95vynec4ryuv9w1cdpu924lh8zp25qbin8nvwh" +
		"bl4folv4luwx5b10a195ietseyfhvf9550mlcf80qqdjmd2t18rkguym9wwk" +
		"5lh1vbaskxlebwbg2etg1kxnow9djk1j0oriijtwnsbr83vi890pxt7ajcjr" +
		"iiaxilhh6b4mnr87cugibvnvcq91lkzxp9e81pnobm10n13de4nvweqhmtja" +
		"402cb3lu3hvx35768vk1k1dxibv3dt5tlhdxx4ugitn8rdngocf1sq8s0w9y" +
		"xdptdkjubdbeyymudxu14jie6zunfhxp387asgwnmj9963d1rzlbsl295c16" +
		"ibjbpyl7y63t5yi9zfnktshin6z6k6gqfrejj5zy980j8w28l1y2347lyj2x" +
		"ozpa0xpyh2xhaalpk7wvylsfo1oz58sa39n62tiyh85tcgd27dz9omia8ggz" +
		"yblbpltivj5wy38j0do2l1iylbn7wbb5vnxvohmhsm8vev3h1ukypupi25pi" +
		"33je95d1t5h2ir530mj83zi7eellde9yimbky9qq2a41w2wlfkrma6emzhey" +
		"t7gg4wbv81j0it2wn8nzajw5sq19q1f1zr5i9i3ug33ht7g9u3kpgxg734dy" +
		"dechalu7sve72tpllzjf6qql188was6ihnuyqf58ylcb6d4q1t3m9w1dbzeq" +
		"30sokt3r7g5liqq019oomcr96hmephvrl3ksqfac0zs6hi7sp0k8ywthcja2" +
		"ysxt3oryuqzvxcd6esi86bk1i743t02elmk1y5zgcnzn84lko6lelg5xkrqw" +
		"cqj3ytznvh2jrsphj7e8v0eho8lswmobjta47z1wgygbzn93d8drgvmjzrpq" +
		"dm158aswx715tkkk2iul9ckr7gt68m1k0oavqv1jx49d0zmaouz327ocvs7m" +
		"w4ifehfqie020iahidn3r6pq3cpfmjaxddcnd8vbfq2x31503shpazugflfb" +
		"4449fc3pee75xpetxa35d5ej31sa0qx344urp9sqgaj4iqv1wpdb47vwd6gf" +
		"ja5pevf2sjsnljaiv2wcj6hhrfkunuftfaegt6w8xz9p6ek49i5vqxjpuk7p" +
		"8agjv6kr9s8ut4gigu0rt85jd1w7ztsel9hvw58idypbku3wewx7qyh37oki" +
		"rtqnxkh4jgxilh6i5ntwnbsdmt3h2yaazmrc6dqh8h0p82ik9dslzdo1kuj3" +
		"tkytjbttar5orbzs0vztcvbgbrwcqq9endsdy9o3kf7js5vzeiu30seregh2" +
		"xz53kg30cm3cls0rlj1wuatooa0nwdbd8y84jyfrrds9po1ltspgun3qjyaq" +
		"d49q0ajwfeaj0cwwo8pa3rwzuybwxfyais9d0frpnehnn4lqwyz2rr62wk4p" +
		"9yiyvy5y8e22os43i8ai4awdxbd8ksvfjz3rb3itntxk5qtglncv3alnx82m" +
		"bwdm7cpok42bi7dullhexmmcs06n4mi9iaw1j8x6g987z0bylzldxgu0v55b" +
		"887xz2rc1tt7qy5wcgl64d8iy72356o97w2lixll61whaad0et3884hxcvw7" +
		"bqya4ksrz2lo2xntvixqxteh387801x69cqixxj6f42jbjkp51th3o5b4o70" +
		"bpjtec5onv3iofljroatgtjejtg8mjkkz94ujleoh4gyfzrsvivapqlu90dh" +
		"3ddqqddz9va7x0wsiatfa082r9cdnz91x4f0hpoh5wzddawlzscxp8frro2p" +
		"jvz2kft9a02ox100mn8tdpeazdls6pz8rxiujw3uvxczolw3js92t7a5199a" +
		"zj6kl3um4vwr973m54w0321ljdneujdhn7un0cz7mj0wzy5sdazn0f3msb0s" +
		"ankuijy07k4jka2ondh7ufiug9eatgpgr1a810hhtdbhgidoe1x07hcrvykz" +
		"j80xmbkjyyw3kpejxxgwxvwtq97pw6ffw2xdt392ssnwqv52w4jc5xaga4wl" +
		"jwps1bjsy6cmtd073mw7jk2bmvau373drieltzgybpizq9ayoocac6rni4af" +
		"ih2kebqeqhbeeajs4wtfp74678zezcwqgq7nz1wkr64moyfqcvepm32ejcdy" +
		"1ccwmy7a0elcupchw98yfkhni34ru082lm6rw5vc9rp8fcnn3gbor3i9xdow" +
		"i1cb1kdz8d3jls4lk3zwv1zcg1vw8b7m73h7379g3ngkggxfh327dyqh2huj" +
		"8qit8gdasmq0shxqitd72ogghgind1btq68324ffdtp7msdrb6kesqwvg77v" +
		"ok2fzdunykq00y1tqj0ryx7mzb2ev0m32rzgcl2p26125lhuhx2pmy3z6wum" +
		"mq6th31m5s8o7z7eaznbqni01zmbzrw0waclveb4yf18nvxj0aasv5malf86" +
		"c0z99gehpg3dpqkqxp4uipapikcjjidrh0fagyrdcnc45rkfc8skdqzwuv4s" +
		"k1135tmjoz7czrnubilg3dgflp7a3cn746u0vpfx0c78285x8otlqq9fb642" +
		"g85qhfoufvna1ob4t41vhu85tu6sngv2zuddxa24gs3cbtysx5iex15r39nf" +
		"9tzqbyctkvz6wiiimj6v1jal7pe74zvf46sq2wx1zintuparw02groyrpffn" +
		"y2e0g89rytb155rc7jlbuvhxjyh3rlyqlqeskk30ar019ts2czp0tc3pinhd" +
		"l54prdf7o0yzntx347jfav83k3dkgn8xpdttkuqa1y0q6apekpg1eive07sb" +
		"z2ng8gvwe5dy8pvnzv476z6pj5xxhtmz3ukzb83hkecmk06cqgn3ci6ro7qb" +
		"viw0kaqo8r5779dsia9t2gkped6qz94ujmtzsjlk828w47zrgwchbig5s9s8" +
		"xl5hcwrc0ytsd7n5yseeu2kyol4sdvgwo86cemcunfi0iyx4f0x5tqqitifs" +
		"6uw5jc6mgfe42tu2zoyfgco5pgaun06plinvwp4yux7jfqr121qf96lprlke" +
		"fnh900oe6l4w2vqvkukm6cllhyzlpugi9qjnpj25x163j5e7w4nimd44a5x9" +
		"76cdqqhq37irj9u2ibhj8gjrtgp9qm6hjoaqmh9gvlrnvi2tf35vmsuuq0q5" +
		"1kuao9s83l7fqq0mnebhxxh7nfn39vj7xcs5rnmqyxnrw415bxbrdoznu5r7" +
		"7xs4du3gmqi78oawz35ta6880gdey34g73iv10qc1p0jm07chw05kxqcev9u" +
		"px6k4v8pb6ar50uha9twp0yp422r428luajrabzrda7qqqxlw0k15esxvdkm" +
		"h9owid6ihnkmax170u8od0aqaa1s1yzhcod5mybdo4nz2b6sujibu5ri0srq" +
		"mvcakxn0c5dmh76yr3yregvg7f2cln4kv2afc49z16cahhpw1lzkhmhcbw3t" +
		"gro29l26c7yy3baqupgn47dta7dw26f2h360ythy0lctnlphz1wpzn2kt45l" +
		"i0jz7tpzfgu8pmlaowb30ep7ahp9izs3a3u1gbwyhsvzqsb8c6lhc0am9qay" +
		"4qk89mjk5dfgtu1h696ctpdz5dpmilyqbz4tlovmc2rv8axvj4zn80mpyl7t" +
		"wk4ossthu69gvd2hzkd0g8we7bg5il5ar83rlpxz7ol8u87qi4hqay33y3p6" +
		"o0g2ec2hllhrnj3vmhoy03z4d4hdh81maqznu3cm0d6gbz1k2zt8iurm62q9" +
		"es9u4bubr7ihqimqsfx7pg8t65r6tr5zo9j04gfikt0uwms314g8egp1f5sb" +
		"yo8nfk1lk544i8d52avghifrzedj9m20n1bpdtu8laguec8sdmw7h8j70vtz" +
		"d9px2eai3d411pjlb4birks7aoy2uzvjjjd5zhe7sfnm2losb4dcg5sj4duk" +
		"79xfcr9pxu9dulzzogyuje81uega8yrdcxvw3g7iex69jd2hy9kb10h0dm06" +
		"7zvjw710m09aogirsevozw0imu7w1fck8xokx5yjgjlaknkkaixjq3o8o2o5" +
		"ps5swvxoff4subm8mcmkntfpn5fcak9wa2t1722fairz3h1gle6iipd9iekf" +
		"806y6nsw11bsx1zh1mhjs94yj1xxvv5pzvmhiwjdw9gyd38wviqe2c2oclei" +
		"8ahwj6g06aof8hwhigtzaycz0ixkiegn01mj3aqk7br4d4yw8l7u36c446f9" +
		"00iznua6j8u62l2qsx9uoi8y8nl1wpbpy96gfbtm8pxgyj3obgmtrkuec5l7" +
		"p65wclgs0dk69y8f0l5hm52jzfy4zr4mfv2r9j4n28kaf39h54jqrnf8r7zj" +
		"6dlrxe5582nug0s7vr1m0udf24och3cx48dcguouabp02mc8dzwsqvxdrobk" +
		"u0bfh7e9u8alpuq6ektamh7dmwy19roif3s872swmc73pjuz2ctwu8cay7w9" +
		"0ms1or27re5qh8t89vz7pp8xktsz2s4hmzjq3z5x8kaa4f6jh1qw1j8ifbzq" +
		"a789pen0bgm0uskru9mb0kbxzecbwmt44q9hyaqz4yj9o32zg8przvqohder" +
		"qm4oxqngenk2cfuy6ih3n51lxcdockj8f53i5c46t69jm2q2owsy3kbf4zsw" +
		"plgjip4n4gnj6glewrewxix4eqd1ejahic9xz0a09r9gpje5fcvylmm1p7ek" +
		"op00t1nltvhc4wsd1yh75l5lrdnvik3bm7qwnlq2pgt799jb1n0yn2f2aab5" +
		"urr6tkgm0mibzpl35l5vga591psxcbtc5xrzh5rhikdoh7vyz6a06ulhrkw6" +
		"s5706tpzkp77nvohkk1x9yabxm8ahzcdtorjgiq5rp3mbo00qruah2rgeevb" +
		"d4rft5jy4a9gpn1yxfdb7fn6l14cye2std7f67bif4yjiu004s9na2zgby26" +
		"xeiuzhg6m1v1cat5bxka255od51whe354zk8m8a8avhcadu5jyv52qd46ksk" +
		"ngqtn0ni9lkbonqymnr8f3a25zg7ugh8wzxqct6o9wkcuz50buna94q8e2k3" +
		"lb31fp90fsc8gkgope7hakduv25izvwmrctbshhm2wsfbdw5v1xcqunezrmi" +
		"cc07dmvd3u5n25o2f5ozomdhrt7mkzh2d664dj83lp1mgobfgx7fpbskddfw" +
		"39d5sqvif5tv9hbp99ppw1olisohhouii7ndfq96dmng944icf36o71spp92" +
		"4iemofxobh1q44tvwddrixfrzn8zn9o5ar5ugqh2s7mfxz14stzjzcyojs1r" +
		"ynk8ykkfcosckkyurx9vo8hx65j45j7uqo3oo50ny2dgu98lxnyzmhtd1yjz" +
		"poyonzj7fxo53zqpye4lduc6mnn1c35cuel41cvze4r4t1srtanmohmhenky" +
		"sf20ctm7hvvuqtbpe9iikeqwpfahnyn3l0kqi7uice2eg9r259tlvyg64hdc" +
		"7izvp97x3qxs6c1ockspb3kpjevejxuj3o8od5tgfqulwrg0r8fnh0rl5zqz" +
		"l0l1l79av2ov4yh6e5grgodh83qlouvbpq7z7vt0259ufoe9wq9y8fs9fb1u" +
		"9ot07x9e7hmkehyd0rgslshnzi3c71rneyznf2ftys9qtk3b7dj4oa0jvnar" +
		"kj8ug3duuo0y6e1no74xtnklghsih3retna4hm8q9i8b51x4p0x79463v6xy" +
		"r10b45yjeiy2c8y97dsa2ke861axn5ety4lniww8sri6flg57zv4j9e0nt86" +
		"julau9pve46al07sxxqxpmhk9u3o2zadm9d3dv15r5dkb30l5f5rif4r06ej" +
		"7xy5qjc0ql5ajlzo58rl7yk9jd3a13aaxzesdsfy9a7mqp4ljkzqebg0w8kr" +
		"aqewefy6akhc6ib33szssh71mofq51kwg6s26jgpvgn2nb59bka5098vijnh" +
		"jsf9glyztsy8zv6xb1yklhvd9mlmauf7r649knvgxwwwjnioelfxmowtiywi" +
		"yzylmjdcqeaghnrxd2iciubj0d5y0qjmgqo0motzzdof2y8yn1pben1zkj7p" +
		"bwpmzxyqaapbp3awnmw964tldxewqulygj1cf9q1j7ah431ws0j658vh0cru" +
		"hvny5plfchlxih9jk5stwokrb9zbgcmzmbjsmfjjbum8niudsvdfc8k97t5p" +
		"vb26rhuzgmfia8j3xwfqyb75ujq0utqc9ytd0h3d4w3cbv6qzx40g5027nb1" +
		"clcympwe4ky9ob75r1kcacbal8et2dz8zac0qmkirgnkyjx96s8rkgjv551e" +
		"5xertxs9h9to344c8gqttzkizfb6gbasp3hxr2iasqe1q6pgn8otheq7u7p8" +
		"tistqcxn8rtqwp36kddwh0s6cqwujz0c9af2rm48cy0p2jfolyjiue8nwqjt" +
		"g720yex520dtc06457nuhiz656k0d453sa03jdklc3yh7t31xh31xpcasn7i" +
		"r208mlkhcjqqnuwfy0seojkqlz1bgztu70w4f61c8iu6mtib4l1g6zdvxd1q" +
		"ka7n7j05nudurz1lwnnvfdl4ewo5clfby5b3e7t6f085izvyzld47eyjt1g7" +
		"t9l5redkrfetwxvxcfdo1zeg43x7952dotyxif7ac7fcvvs5k7xo5pes336j" +
		"y6f3ksnicrw4arm0fe6l1uqncctx1kq3adfcad82xer4dxizgdhbrrdp2dno" +
		"wtl7usw7js452t24q6m2d8isree1z5s61ep86rcb1z6sa2a1mlvf5nrpc5vn" +
		"nn30ftl2v2jcqnq40ogtfbcgw52ic4puwt7o6mmknesfmx15yfl5r7b1am1e" +
		"7mj5mejpsla75iznnbjf92az7goo196kajmbunuanh2ee86yjx7q6fgcnghm" +
		"vvphckv4egb7h0tb5mvfe1guhjq2q65nnyr31kqtxtcqq2vab2y4hnosidt3" +
		"ffhf0rjmyj96502694yafzehvsc79eehgf48s3007t8ld72tnazwy8ut6pxo" +
		"d4n6b11yhzxwyq07049bgimhl10hamzhj1ldmec0pexeeo89p8ic55y6vsyh" +
		"cc4uuikoqc15oq55vn3gfmma9tq2i6f0x5x1uop8fnekhtiy9qg9uixfhxx6" +
		"55drcsxelio5vqd3u8eq11bdy2l2ezpn0zrjrdblukegui6pthcbrcxuberk" +
		"x2i6a22o2lwnwwm7bki65yhxgwnlpdfd1f3b269fcjyuiw6l2ne5hkf4y33j" +
		"xg8pb3dnxspz6zif809bnynogaavwv8exf4n4qookby52njkp8oheigfxz8s" +
		"qu3udv1dkyn04i8p8qwyz312avrz234kg8jzo2tf3d673lix8hyhfydp1c2t" +
		"79svfxx5a6uluesi3q2qxezw24fraa0344bb13h0hwblq4iym713jr6g0wtu" +
		"0v6x8don5475iqgitmsgqx5atidf2lcvh695kw7kv5zsoa1bbgbuou3tcr6k" +
		"zd1xx7n4c1qzi4okrhd0cmf55f5at4goz7xqqdsnjseo1hl0ujfapmm8vwgs" +
		"ar4e5mn30bwx9910vuxq4wkm5l8cx1u1i93y5mhsk75s1p3bjp9f5h3ykdym" +
		"wnjs6h1mkgfrh603yqbr9swplwp7fkqekwuufy7vqoy0tr7ohwhbz2x8su9l" +
		"9g75aph6rgy8783vgy6yiwo9v0e0jj6kzgtc3kpomw9lttvdy52x4g6urr12" +
		"sqhd9293pq1ccl5zx9rcy5q2yrdb4oey6id8sj32y99mfim4fz5cuqbcdaa5" +
		"3xdhj65jteigigjqydu2cgwl2nkftiobmjzh4iipmsa6cxcyqlgd10o425x7" +
		"h6aufrnehr2s7eoc9jzhtlc9c77456vgi0wns1q35j9lmc0nidifzvd1ycxk" +
		"pyfkfiezkli7n9kr1m9eceuxvdymyty915ygpau1uzeakq6z5pgf140oqqta" +
		"qc3hgol0eb99x8n4f8ovdy27xxjxh5tw95dmfpygsq6i4dx67d09lnnq78rv" +
		"2nl4q5v92rid2yp1c5vytudh22vinpjg6p3ui62wrv8x6rl2xgc1t2vhvdii" +
		"ce1udgemmrv0vxm5r54z1m1s598cpedhih2zg3256ucf241h5ncop0higxll" +
		"u5nm0m0rdeiaccc56z5thjca9zlyf829hxo1hjivqnyd1qtxv0aq6sb4a0ll" +
		"xxqlcslc700qj8olz3qajn307kshneoi6gs9xupdtj78lx4oei2lh968w7y3" +
		"x81x7edvyaos2n3v729ermqhh8m4lrh3sarby7kbrijo238xr72npa4bkzje" +
		"zppid7qiuv57u6hjk0im6ven1byzfcza0kvla6bav80e9b89zpiccjmtim6t" +
		"9rhhls6sj8ycgv72zmfwibqbd6ym4uuj92yo1voeuux38ts0j73lmtuzyv8t" +
		"sezzcx4nmh3f590at6jlgvihcuc6d8asvaebf4c4ahocgtgfdi6o5doiak64" +
		"hncfx7d8pi813kmge0vqv3eyuh87imcfcepww4hwuaqz48by7o3t45s0cmqo" +
		"fz6au7gepdpggu7uqsapwnnlyf52k16csv2addwq8s1jscbkliims6wjojn8" +
		"icndzkzdrt3ng7406sxtbbnajdzv7ynw0cqtl4gt8qrya93qzrnpz65zyefy" +
		"wxqalrwmlpjcnh61o4p15zksebemqs6ob8kpt6aoeiu58b256invezfqtqbe" +
		"dlsnvjm1soiuqf14q221py9l4dxqlft96dqrnc1a63bmuscy8ox058by812n" +
		"ybusgv3yu3x1pgr9934p07qkli0sc5kqez6x75576n1ep0fmtrk15r5hyh7i" +
		"k2unurv90726ppsf5isxp4bndq11twxjv1igfyourh2rr6ytogsmgdor0615" +
		"pisc8t3t205iuiw5xrpq5dvxv830qlmlp2ni9suyf938vaazszpmzgsc4003" +
		"i02yc5wb5tx61l9fxehylqolmfsq5fotxg3gk6d7txdlsbazysgxravmlk5e" +
		"41bzvmojny0jo37coab4o54evnnt1exwrb3uv91xm5nop5mm3rsy6x22o796" +
		"333u6t0r83jwiuvw5e2qz2tf1lpvuf80t3v2mn6r2fzqgqdsn1fdr7b0kroz" +
		"22324hxcff68n024vq495hm7e0hwc1tn7y8qcmxhaxpyzz78gyclx3lofaaa" +
		"wfendvmf4vl6cwtjo1h3niqzjyugi451um5sqhiv0mcxwmemwgvgq0l1zgjm" +
		"edxzpm0lxcl2yqknozfi3tm7i494b10069wfb56prvb91bo6vw0uqnwyhwvn" +
		"4gtxk2gohe8pxio3bls6lv36u0r7xtimwayfqexsmilsqobnkloeb23q6pvq" +
		"la6fz8zdfp6pcijlptgpo93ni525z0bzgsrsl21xr2ycof7u56h1401s0ogm" +
		"21tjwxr05kibastpu5i82pxapfmp7smfxqe960g6bkao83i9w6u7gt8p0eve" +
		"jtmf04ai2z3e453j42a8doulyfh1vbzigqppauwpkry9jtm4mpesy1g842dv" +
		"g9g2zdm0uvy6hi70mccdtzlxovhkf0ly6rcj4fmzpm3ssv2wodm2vi1zmqqi" +
		"8tqv6dih89kde97qt9lt4bhlyx6ggnkbikyxim39cys3gy6p7o8djsrn22nd" +
		"i78b1imnr19vijrr545p3dd2227n6ywesqig223lzc1aahye36levghzeego" +
		"fbtx3sgwi9hyeqwn7chcfz8yg7l8p4xavnv5dxk3f7dbb7wiq6vuqm85fmp9" +
		"ael35dtpx7n97s0fs5uiuwpiae3u2arz6ow5drh0nevxxi30uvo1avhcyeoc" +
		"kuzezzufo0hbyrg7q9otu7t344ewide3pqeplu8adhx9c035hp22gz3dfgvk" +
		"0wesizxntgsttrv7200o4f5pb8n6y00tg6apoby8duvzql59wdncao0adfoo" +
		"9h76fux33x9qwkfk0gaz8jkk08s10v5szeu7r2zwhb6mrwcnwzs911qhv9e6" +
		"z14ld48iwim92dfllxfcr3ametvrac755odyt9f0en36fiwi97myklckmnnq" +
		"190z8zy4xkcftpccsiyz3keozkgsvbajtj6c3fiso5dschwgu9z5e1q5mqp7" +
		"oaw7eeizd9176ryth1104s3fhgtbtq2nj9zfsj88s6cd5qh7brhdtk1ftfit" +
		"fidmkd5lswajybz8ki4qa68yoelu2lq82yps3acyb7t1flnofymace7lvyug" +
		"27m4sxylxfa9qsbceq5kjfyoryy56uxmmyd0sly292wg6rrf45fjlvciodo3" +
		"vjfrsst8njh5uzyml779k7tn5sdutxt8x4fvuvbncb3pbytu4s93z6cazz59" +
		"tq7rwwogvplgurlbdgzm9l6zd91ulqi3ay0o0bpx7wjcwu1j7dia8y2t0xaj" +
		"tiiv416vostfo2vqnu4m96gmo0v2uupu1wwan8nlzp4xjs4cvua5546ckng0" +
		"fxr0cidxkhj2g68kjgsahp4qg8d0526biehogi0whdt1wey8g2d6xs8cr0t3" +
		"sz5ra91t1p4dilvw99cra33qjg31u6clk6yz6ler1y4d3p6dgu5gfm6kzx1h" +
		"hndqe4v76fnoh011pb9vvi45d51f5valslbhb129r5kfvfu0jh8zryka7t4c" +
		"cimaalesq775m3hsahrner4s2197fneuz53npuk2r973nuh8hwcha81wf4vk" +
		"0y51vd0sd6w9wpshv9kxj59zlclb6zxz22aaz874sp3um834hvf2ts3d7fb1" +
		"n1l1ldh1qbhmm441z2t7h32jqv3q4lohbixydfvsbyf2r8902orqj0oizzmk" +
		"ynah2u5320bvurfsdorkq1uud8nr18cozldl20nte0msv4vjvvan3y65pk7b" +
		"xfqgn1u9meudsw9vditz33a8rj53j3x020s49tp6mj3e85kju07ty0xlvstl" +
		"euos1cd5omqxyausocu1id82ifw82f9c3w7gfu84t7czft8a3yy8c8b47bwc" +
		"ymjx16s1aailit3ifnhugak9zgyxosxch1dn0ne9z1jj4vncfc9x2ouqdufs" +
		"l9myghqx4fzyzf8mbd2r4loyuukrq64h0ix98b4pdtk77w3d61p2tp0k8ai4" +
		"4614zcvwmtjtbdvkjl6oj4knp21c540wvupr1428e1wpgsayxy4diec4doc8" +
		"1ejvph62xna68nqqd5oi3lr5ouvssteiuhk12imvo2ux35hsijmyabkeq1x5" +
		"7mrq2ozpb8qlxddfjgy9cjyohljdp39yk1yyojgfajc1oofj7sojrwhiqi71" +
		"7qvdga6f605bsqovmtc0u6rywdkyc3lvsahkkivwrcutm756jd84bua5fx3j" +
		"nmd6k3qgf6yef19mggs3hvc3s49qnryyhe04atkomy54kxmzs9c50gy5u083" +
		"t5wh9c1l7bpe7w5an3rin2oo21rq12y2ts6hm91g997dxck7dk87dgwfdkc5" +
		"sj9m0f8ds7oof7ywetic34gg0zredli1robrw1bm1djz5ijlimalgi2m6rpv" +
		"fd7kme1nzouro7vw6b8vva4hq1pz850ao5l48o6j28mnx6m9vwwciq310s9u" +
		"w3gqi314ffwu9ok68184l5dd8i5aecwobc187v5pwdyl63d88utra0lm9380" +
		"wm4ut756myx3wq8ok6nyyeea0khgd898kbq7j64fwr4t3hzhxeyunyzi3lmi" +
		"uc7s54h7s1a7ap0yych97lt94eg655vfu47frjeo61pcpwrgwu3k5ut2xpwl" +
		"v7o023tz1nju8uieni6y0ccc8iiovjp6bw8der2w3z0imj4fzitvax2oqo7q" +
		"xyl6vbqzehl4b0qsietcp1vp0v61ppsm4mlwuwkgimu9la6m60hplzl7vxah" +
		"hvc9mj14aq57fp46vmkjpq6pqbm2kgtlshqf7bn7nxpvu4y1l2el3cto2cl8" +
		"or1jn3w2onfn3n3byj8jelsvur9zz8ilyglm1xsfac2h9o91hgm0hoqal0jz" +
		"q8cs4mnm44vhr32r6uxzxhe3i6e5qf7vjrolv4pstu4bqludcazjj3icstyx" +
		"td27l6aw8s62tw236swzr08mmxnqhn3hz3luygvz40506l6vibppytrm2hfg" +
		"jdpgxnu017te4brrkgqw0s49sjctec9o3npkydl8tiyd955i793neamxj6qw" +
		"d6gw6hcxom663dmbawkab4be12h7pkk0m5jztx5cv6cejkk6z9kdowi4cyf9" +
		"a9u7zda86co14af3jwhc8din23vkw1oruc0dllym6icw68gopnqldwpk51xv" +
		"0lkfbdg2evug0m74cscwjauf3i9rzwo5kzc1pewe7ilvj9n2ohcibool21dp" +
		"t7xlf1m02kuc7w3fx7hxrooxuts8nuysjwu3hetn9fntdfh7cbykt5jjdnag" +
		"mompxzznhov66twux27ozx0mjql8ypuv8w9sluylxorlj5qmb45xd9ssqx77" +
		"depd0816f94aaixgc9ws5inc8gy2dg031nd6ilbkkbyiqsgzyu0dequy9tei" +
		"3sc5xoclgow9ir6i8v7bx4oqseeubsfyjcub5nm8kmjryxnv8eu6k9bfvjuh" +
		"oday76q2xuvfzn8lcmtql9jui58og75am4mwizkyxg5yngusq9k3n8mpzx2h" +
		"2xrnzr3wupzz0m7pywwi1cm4kuola5j6xnkdvmip8fidbwgm62k3eoqgt8kz" +
		"wedfe3sxozd5rjvlfed8qgxjixxo0y5yj6oropavxphop44c97om0nafu2ou" +
		"22wyidapx8whtz8k91fe0hysmfsm5gwzz6fe3sg8v4p3o3ju9m53sxiogvgl" +
		"g896fuusa01zvb0v9ytir4q28v8c30dbik3cx0znv8onpvkewecgk6nb4msf" +
		"d0mlkbblgrr118de6ibjwyv7byzb16nmgokc0a5avhpbx0sx2nku03pep6kr" +
		"hlor059z3qr6hdlyt0l8p1c870dtfnwk0b5a1xagg429g3cf9eglo3etab0t" +
		"io3zbrk0lp6m9lpx6mkuvvcq3fqa6iyg83niw6k7ha8j1jmarmhsx71iprmo" +
		"izc2splt2tfg1r0d1m41xaq4gstpejsg2bhetff1p8rulxgi6fijgjk3q395" +
		"dckk1i4d2k0d3sx3rjxnz8wqdbujbdl4guxm22lhheq3t5eno8tn6brzmkkv" +
		"fnbs7l85c2of5utp6zrcts0p5ory291ehz42vlmfwkdqv54pbbhz90n6uk26" +
		"4xoo400c5b9g0cnylj9rlmp0oufyfq1fj1bt4vt87g91ijzh23oned0kymat" +
		"2jhumai18qko134qu3fm8jbbang7dgvr04yck55gb8edp7eme0jb28adr4hf" +
		"2utx68wm02z9ll6jc7z6sfitfmzrpulv8t08kpzjk12x8dcw1vgbq8456lby" +
		"xpjryr23kqagjbmdxsvrzyr71rr260wzi34xohl253ie8a5ome023o64fnsk" +
		"hu70tq864i8o5dbu2op8gvtbjy0j6k5hp73jal38wii98934voslam5f1hef" +
		"1oksxaq6c6l8yg8my7qj91c53204php0nue6ln6znxd6cim035zq88i4eh17" +
		"g65r2mcdx6qbdbwnzol5fsbhdm9uj8ez4f9lxcj30vpbpgnwt33iajafhxth" +
		"emsad2g9rkipd6kyenwp48j5f8v14jj74ggblkmhol960rpwxovok5tk46uh" +
		"tdb5ygbfrt68v85saznn808nx3q2ie1huhm9ociwk6l7udj2b1j4ldwyl3wr" +
		"w4lmrj95rftqxn2hsa6pxiwr8u1ywvvmqh552cfaiwy5yzi5j2u261u6kio8" +
		"lnms10zrtdbjr6dg5oyhp1cwfg5wuibx8lvkanbu9geeapro0dz3vio4izrj" +
		"2aeaplnizj66ks6lnstp1g0z5hbao41k4v5xxvw52artltm5b9nb46hwn8yd" +
		"d7vvykmcoh830zvzhj8o1dzp3uw0t2b4a4gm1knqxedds208etvpagbhwcml" +
		"li7eh9mw5v0neen9bej7b08l6cb4dnvityvqf79c0zrhud6q784s2bpzviqm" +
		"ss01pwdnjjp2ph567ipundmzrc7k6d4zhknaziag0a0iocxkaxqdjrxmswfy" +
		"zwy16st2y5ggxa7e90p6tn6nz1is9ms6hx6u69knuu3dtzxjmn93px6estyr" +
		"qhhoj9d9wtfztz7pc5awzc0xl0sulzhlae2yq6qau92pz5imnsms28t3ugpa" +
		"p2bydzcooxhp012qqtlgypwarh0zxg3fx0b6bm25ttz6yatg9nazl0kay9fj" +
		"carq0s3wmxa6bxazpy874va7xtw46rc9dou2snvmpd4urqjimbtfj55b4psr" +
		"nbsyfe2fatvsimomo64a602vs8hvo07dyuavyw1gj70y38ml1zcvm7l1ptae" +
		"vj7qwoujjou4kaocycvpef3o0ltna2u3yebxr6ccr2319scmpb56357s0qy1" +
		"jcz81s6vz6os4bmxrxdsq2hft9dow551f5656iquxi2dqd9mcosky2pvula0" +
		"s78fl0hnxgl3yfuxg6dkuvub8enx9fsa3esy05vm0qfzlp6qmc64vpwhoyfz" +
		"268kroe68dyvys4zuo3rk9vwkj8oqyxbnz574xmnoe9ff99c95rwagraue0i" +
		"2l9g0u6bybn2zww2de7zzr3k2e94vxbepvsv4tdp0lnpl53ywbqajia96yj2" +
		"nyc4kxg7ssjlxc96eb4eqvloc0xocpqtietcn1l53m3r1qpu558kk70d1t5d" +
		"03f3r8q20347ljfto624soaa9wn69hgfu3xbhg49by0p8kdgdnn687cltnlz" +
		"8y4tjltn3fvui62h52icndtftm6zpq9zqpyrfvfq7pls4gsmd20eyg5v2oo9" +
		"tnl9booborsmvv20fqaxo15gi8afoijcplpcqkqdc5uawtovvnk3o7ctp2d2" +
		"ce24un6q78esvo8obq8dngaofqttnpapeds3nhr8mb2qbpyystjbw7it0mbb" +
		"9aay53wvybejuhk2boarrxb4fg2ffrevwlrgj4u5hemrc8d50985v76a81fz" +
		"8p6829f6wmufwae4melos7b5sibsofjtgetawqsic759o3ncbk6xcl70ednm" +
		"chmxx8oiysf0ux9jz8qqybkzxscy9ikpwgmwmkz3v33w3egsemxx29uk00gr" +
		"ttmubr8xmtihezpa9x7p3q8huciechxv04c91lg7r6xzqhuka17iimyzqa7s" +
		"eivo7itlliiqhud34ipsf9je46mme35fvt1ww5rpbxd8kb361endmfa31cyx" +
		"albcjcll6rqr3e1p3g9qqizs51ekhnh8fsebzdpy0vxcoj02gxlfntydfcto" +
		"5r9iibcketctw3t0gkg4h1y87kij2fm396mgk9spu3vfso24vkknqhsepmlz" +
		"27a8visnmj7ymknlm9kezso17ubm1dgqscejg0wa8ejm69t6vj64j8wsq6q6" +
		"poeveik6ni59p5p932vud6sqgj1izi4n6qq37mp5txd6lr1v8rnctwuaetby" +
		"n8remruu89mw3dmr4a6vftr4fi4j14gnx1ohgqhyou6priwbw7w7rvczxp28" +
		"y1wph0vjpwj9hu31dhmdlzauwlbff0h6wv0bxspfqu7cajbma4vwy5vo7id4" +
		"wzsxybezib8ffhhoyp5r9dk93y7ay13p63p8adzeqozvdgk1iqdhkdc7houb" +
		"bh26x039o0tu26p2nkhyp86ahhtb1l43qxhdqjyjlrxkoo3ifnmmy0pv2vxo" +
		"fsd9n5iann7d43z0fyu9snxoxt1126lkon0akga74lyf3kix5vfwtelhbeyz" +
		"vwkuboxd6rzqg44cvwss8x261k4ockai9icoj8fl03dffw1vxi9f419aevm4" +
		"s50127xnfqo6ou7omv357vt2f6dhonrnk0jgf2p02ri9258v5ecwcd28nilv" +
		"j6onfsfk1eiqt9hh4rmglhgn6u05mknjalsk2de44ry83h4nclt476yzowvv" +
		"zlwcrwltrihuxp6ypkrhb4jl16t2jic1g4kqpj8h0nmtp91ypeyx3td8zaxa" +
		"7nke5uqmunu3wln3w2zrvpk7y7vjagm8sppqkptpmhi7cb9nu4zltod4oakt" +
		"uig5q24h6jyfbwxfg2rhau1tep1cwlvj40t0m8t45fsu7ky4f0sr9gp75tr8" +
		"n1fqwctfgrxo2z4dn4lxbpvxqdb4m206b1tao4uezm4flee7xmiybxoo2fb9" +
		"fyobsvfwprcifmy4mtyncfocgayzjvmj9lgvffainksapnfwo2av4ki2o4na" +
		"1tusi2kyshc3v833w29k5zfhsemny59jzqgegy7lwubiwff6o2spljx2c5yn" +
		"amhcb1dct8dv2evlz4ig2fcrsaisulbtpklh5q90vdb6jcdyg5wezqxqqm91" +
		"665lv11ecg9mdoowqvcc5hh9x7o3hblxm0iz52zn9iec3ynnosoguuhdfmv3" +
		"rcl97oiz0wexfr0kji9hkv51v18jobgo8khzc7bhlw5zfpxvs8ct4ra9s8so" +
		"bfa71tg65hi4toeh6tw0g5kpw0rsqw4stpwu7obqpypmygbiipj143bgpviu" +
		"1zq17wi6yfzmt7j4bfsekvyi0vdmcefl36k0w47x8ka5ahg86ab6vizw8tst" +
		"05uuwnmchd9yla5vxb5aa8n4ahveb6jjlkin3jw5iqygfynq1gnom9j9nwpn" +
		"5q24tn8rfvixwlsrbx1mawk6q5uaarjnhb6pi75n81e7tv6kb74tj7r8ftk4" +
		"vfgbeqly5pwdmfdajuu8solgaj1o60u5iiteyuymaz67lulyl2hbft8ww09z" +
		"9bqrtovfiu5ogp2av3ujhetenow1u01fiab7qf4p3g27w5hk8r5q8ub0fddb" +
		"je5iqb1uij8od0lecmtriy5d4zt109q2pj9wdsy6zqe8shfc0w7zbdmx84gr" +
		"6v1tua1f6ts0zqxdpse99fdhuwj90u76uljbgiy8k6elu2c5vopx0qlvqciu" +
		"1b681s846erqrwiwtfdi796sha4egfkm4pc72nvmd2zde7ce0fngvv10s8r7" +
		"c96i77smvo22yxxdzubq5fn165jc2zuaxzh1ios3kndn7zmwu4y6b3vesdrr" +
		"0je2lxs891zfpm1rlbil2bx0okyh6iqytpn1jpg5w49f2s24l0vy874armv3" +
		"7mu099ik74kiql01w3qro8l4rzzsvq4qnhc4ab9tp03k8384snooxm44ooe0" +
		"jo1qvtircug39eo9e5183v2b4mkuphb5qnoc0fvxk63plb4naoa8drgbvnop" +
		"x1x6jfzxn0hvxesg44d186vw23n41ckaj1fkve6zc2ujisum54svgadtz08d" +
		"nwttjb07ukn1n37w5hls0s60mdgf4ywd74zhusyxdbf3av7fbd5fn6qb54wj" +
		"9rpmvjgr46fdrvepf3od3vor97ktmrojt5xkwp9cl9rv8kzviceszalet4g5" +
		"w7729jmfurizeiw16i2jg7l3tp4f8fj0hzuvfyhqowyc63dyn95bn1b4s4xr" +
		"hbyy0zy55ox2wq1zzocjtlq2kduez4q11rv0gl6a0r4mtkwlc08u7n4rkpvr" +
		"e48i0yza03w92mmwp3et7b4439hxj36bhhrsxkv9onsbkry6uk70tt7odblf" +
		"lw0s9v3s8y5nadypg76a302r29lxmcile1ac1q8jfe3cuflvr743faqs8eqy" +
		"x0y0rqf4wgadnsuwhmtz9k0hk0521qliildtbx4e6fnbzucegib22fwtmrf1" +
		"ogr3bjppy2fqahnm4waiplrwt9r7gm43odqqcuumvj4nd4yc7sb1gz9o1j59" +
		"f2eq4ouz7q2i8ueh813u1c267h48ep6ljm832eaobv90ik8uxccdp2rw9556" +
		"tr5a8clbm8n0fvgn4iu4a9vyfkpv5dy9j51q2avblcbpr7b68rs6d47jz83y" +
		"h6gh3mmc91i0cgq02p8sj6unkvqzd3t380ybfbyvygc457thnatdgd6f0ai1" +
		"yxz1ycrxbcqeg5v0065zz8se0aaonkr3utuephfbsc8oei5myp8amy4n24xl" +
		"9gd1tkmrgmqe3vtz7643pmje0i1fl9pzx9pv9p35vvb66jsg3idr1w1mtw8n" +
		"qn5b517vgz1mgdxxifxy0xq4d3c4ul2f6ybxshrugrpbqvtvhxpv3l0g2ezj" +
		"m5z6fvsxkzm2v0ieqrmpv2ihaxgntzqidga7xwrmfkompcvz95xfi59r1vgr" +
		"5v48hk57t9x5fiaoy52nn4k924khtsb62brvkbq8xr522mudomzxmwhblvfa" +
		"pf7jp423zhwirnp1u616isbzhss74dbn7gv6jooqq2w8ilbw2lldm6rnqjxq" +
		"k5m25zmm7socp8gho5mvaxloue9ujl1pglrpkd9trm2kkclbuprcpub8ccez" +
		"t1x33tqzo47h40z8inw75642pwkhkerdbl96w1ej2ilgcr8rj79k7lnezmr4" +
		"x13bfoofvlwqn1bi0put2ubhhaaaqmfmmnzkap7qjm8rrjzntrv3xfi6ix06" +
		"rsbufd8u2lfg961qr7cyaf6d120bhoze6iyc9tm9wq27wftalga4976c3ew2" +
		"xv5pvjl51dsnqg9064r8ibey32i7qaqrb5xr9evfjpqykg1xtpsi5u6x8fq0" +
		"pmkpo46w84s894uzxgk5vvsal55tg6otgs9ibd6ftj9abgue2go7brxccaed" +
		"jx9mbny1uwauc9vbg7atmqvfsppg8usyzueuemlk1wj2ofv6rpiy45ka42hn" +
		"fnp4lx87aiizm9eme4lzpa4iysotlawyh5zsjbskchzvrtpkawaoyctwmrx0" +
		"gkc0pv2l4ouzviot314bt8q89tri2ss8lhjy0daju020ex2fz3sspbua35z8" +
		"y4q1g0tvla3wp1h8zs9173d89p9eo41obl371jy4rtc2gjac4ogwdgjgq8sj" +
		"4cvgc7a9331ubxy768hu9b0h4dkl2niedaql6lyn69tt3ux79qiby04ulu9w" +
		"zpyp5o2dan7chu6h7rcro5u89g1nt6zi0ebbb00txhompj9rfna4iokc9wa7" +
		"1t6l3prknm8z6jxe1dpfqwq77vj8332ohjkeopmwag8vjqw2v047k6ybxqeu" +
		"nusnd3l59u53mm3b0p83vk495j81dkyvgekp8mir1fvss8z6uuqkiyi7semi" +
		"bys4r8go57kv0chxtw6lwefrj307jp9brz4fy4oneoinm50676wcoy7nufxs" +
		"2po7ade8o34cib159j8lzf31w7wpw22t524uu55jd4m74o9bzm411vrjvbhf" +
		"w2b5g9z7ci03xd0umezhsgkp2ddqatzg1w9r72d4qm2vwl6ilyeak0i3ansl" +
		"enjkqtqabhzjdfltpc77j4fkpr9k8526p0pyek6ukkoxa4iipkehuxjl7j5m" +
		"bbamvoohq1c55ga10xrmipgzeoesnvc2ckuobw0kf7lkty0awn7smpekd1em" +
		"oz53svojlegt07n11rr0q060t8l8j5ki4992o63jmgqzm67uh988auqk36t5" +
		"uz4dajmv5h3nybvl6bjgvsf9i61y27q4x1dr9atuh6lu3s67e4gqjmpg54bo" +
		"1dc830imn5y14e4ehvbi3wuj3ke5c1q6wa7av6fnhd4kzd7zikgaqt9k29lp" +
		"tuqpzn5me5eekp1f70123h7oweknqqffvvnyzlqxvchxtb956u2v1a2eddez" +
		"ve1uu9wcy33w14lcry2tr8xsehiqedsr2bn9s2o0cgl07ngp6b7iyrj2q0cp" +
		"3xljdznzavgkdos100zanuau5a42mxeytvhg8mz3zn26yjok2fpg0vcghwme" +
		"d1kmo6dcymygb5dswnltfinbdwe9h1zblvzbkbru10hsc050oikfd4tn6g39" +
		"ijk74uhlkkusotax9hmm2h76mjqcen2hjbwl11015mgqkh6u9ildvaakes4w" +
		"f9zhjxgvzxgsx2xv324z1xzpfq90cdkyu5wvc6pfd1arr9fzk9laggexqas8" +
		"y337p3x77cuhsea2d8moqupqwe38fgq155jesurbt71knhenp6hp00knuwhu" +
		"fpnk5yljtzd2n0j2kya85vxovcetppfhaa2bkipli381u3h8e1cqlfrlg0ut" +
		"868pp7s0cij2pvek6hqdspj512va7o0sq84v0w5najjxtd9y4pclcr2256sc" +
		"2xeen8ux3zhkr03jvzy0u9je701f3vddmhc5kaxx6lv166j0jjqj0luu9hky" +
		"4719qbfnj8qix38vxtd2hqi7agircy7b9szg67qut2a82wa9i345k1nabfvl" +
		"0oeelnxgs3537yec92xugv7lg9e35gy6ewbjcrxs2u6pr1zcdz96nye6elag" +
		"0c8cwpbpkuos2lejqrx3fkwrljmybstdjffyezermcv0o5h7035ie9hx1qht" +
		"9gvbfsuw282e8evz8hdnom93lo0a9delz8hxze4dks7qn1hrlx4520m7hcpx" +
		"oz6dkyuzk5cspsf2ojavq1u66szw9eykhirwdmhr3bxozla6pyzef9pojqgl" +
		"dmq8jzj15919lv987m53kjbih27e5z5jqjrut96llmq27l7n7za95e2vytw4" +
		"1gfrx8i09973ejz0f3w426m21wkjs904zbtpuddsb3si0nx342qu5r1y9vcy" +
		"rjvo0wzgajc7m1s26r5wnaeh9o2pk9khtkm4fy28w46l9h5r7tt0rt9rmytj" +
		"0ar1qi2h0ueqtc6o05idjg8uif69qz6mfq8a92vvf22c2l0iuyxd25nzs12w" +
		"bdy5kiey65u8jkp0doaqaobrdnkmp5k16k8sm11mixvl0x1egjlje52y4w6t" +
		"trkqbqzhqbbxwlw6jn2ihsim47h29uut8uxq3cv85tz1cneumm2ciuv19a36" +
		"phxfjsmshak0udgd1k7v9pzvxj2chyy4cfns0z933y32fb6phk57dr36c0ma" +
		"pl0zg4wyzmy10jzz518tqd7719cfvmzszhby7waohtwudgc37q4ahlwx51qi" +
		"yvhx1brje5qsyosglhca5qxy1oqa39f974ebdgay5kge3efxm6p3gqtznqai" +
		"l7ktecchc8c5trssh66neekf1tctzk4icys35dgdo44hvza491az3vmu4n1u" +
		"c15u9oi6hd6pt2qk2pvjbwbvwal0p482au1lyb5sn3e1oj4y79m37yt79di1" +
		"xqewbg3l7tpchf4f10oyungklomycgwcy4tv7yek6og8vckgf8u5zn1p7x4l" +
		"f80tf6n2bmbbfxf0wwcrnb7mf8sy6ruop8bol8mwumc3tyvwyyuw511np1g7" +
		"0425odrn5hkwgwkobi6x52d6ixohrsiglspbuyhsuukta6b4a7scmwydik10" +
		"ava256xaga4f4l2j5qo60as41qk52h8f1cu8tpk79m3cl5lgqbzxtok6p7c4" +
		"3e4f1lwaqu96sriaunjbssd43ecm8423t8ue6f66qx92p4ikh7vh4bm26trh" +
		"ck0vshrmtw4xbnooo05z66lmmla6gkxwolmeaiohbuexx7hbdn9ruijzd6bj" +
		"3r1lr0j7f7q8k1fk1pcncf8xx0x5a7fapqml86evou48twmposwg5ut75py1" +
		"bc0zr8bxxy5o4qzpacxcrb23prpjr0sxipvjnuwvupv09yhldhpfymkjxxiw" +
		"3robs27g0dy1gh6f05m4ddk15ovu56yhsovohfdr18vjsoc0mvlucuin19k7" +
		"i09hh6eea3m2v7zyudsia90nww20ozb91f7q1kvpl7mu9rliykicrhtuqy9p" +
		"gyti23g3zar654rnyb681r8mpza2zcfcne6jsjj22rab1euaeyrqd1bxkyu5" +
		"skvq06fw2smffehwfdcxnivrmv7n9qnhcb1sl19ko5babt3b5z32kowbkpb3" +
		"ylyd6gml5eqvk2zajhgxmg5imx2sjw0uh4xz5fo6tl69bhwwqer38tuls2xa" +
		"dkni7un8a1w7xqodznsd41mej5mkubadfmf1okc56afaylt3qv351p8ujrju" +
		"laa9ptfb66062791q3lz2nlg3qyo5tnhpe1x9tkhndlrt48en7dcohn1fzue" +
		"clgm7yvshiprj8tvnvgwz6stdki0am6mj9eo1ekjhr2s97v1v6o053vg0vho" +
		"cr32p6zpo0nocjnils8ydw7mdb97opt7yi0aixh5ae4ddzxbw0083uvlwzne" +
		"sizxf312gr4s5afodgquykm3ppvppoc7f3tyhawx5zdmtwrb6afdqb6mcdek" +
		"1friete8l7anxsd6taszqzg6dpww86twmiibmwq9lg1fay2dk82b884z845r" +
		"dumk4bnfg79b5e9242elulrijeyjugueh1lbtbisd5uyfwhmntpdi3sw3pru" +
		"26apfq1hlgi30duy6evyncichdc7eyz0db90t45q6jqa4zs8vfxpz9x12gm9" +
		"hz5nrteesa6n2s28bkemykgmxft41sm4wapgwobp7l8u4nppbn7j7hm32n56" +
		"9fnjkt0acxw5lmz5uvkmxw0qkq9qj4g774194iz8ckm5uobsa346tez268qn" +
		"tin2w2v5rmwppy7tn13cp1je4fnuhe4mfxfj6i27ya02jie01inu3kkfqowo" +
		"5su9uz4jqpvh66lwe489a71weywnsjc2zu5q89cvm6sxyrxavm6xav3nirri" +
		"ys1gwy7cufr5fqky6ma3lrpy4obq0e9m8aeu927qk9kqpo2tauo3knchu7ly" +
		"ih398kk86koh9u0vuy273jlzax60icm9adhhzuo1mhw1xilln61ei6rzsvei" +
		"hj6o373gs3a00rj0p0q9w1hp6ani2lv136oi57b215t3ty5phvi8yv3ig8hc" +
		"hm7d2ttbi1qyh9cpfs708vt4pcdo5p606xgxkq8ezzlke3intpce3096zd90" +
		"0bg2yu12gndfgb5k69kxzohhsm2kuc967g15kt96gzsfy58c1f7led5zjr05" +
		"y3dq0n9hrdesf83u1qzsg105a31zk0hkdwi0pryjsp3soxrsriywnbqhe8ap" +
		"il096pzdrpd9cpk90axtsuzq0mas7y43y5erxjmwefrd8nb6mn2cdcehbl6v" +
		"r333hwl5bs1x6oiu85cky44teghx0w8ajwol355vcq7t3klifasapujkq46e" +
		"sefitrdmcjuxejwzajac6m0qq5uoyvfkpigwfmsfowkz9q7v73tb37njf5sq" +
		"29xbag1uyp7vfku167rodmaa3rpe3snscf2xscc6jy2fr48cix68u01dmse9" +
		"dc0cb422yx95kvrrl2b47fak69lxol83oy7lc6rphvyxzor7r21hytkld1sa" +
		"l8ef4c0j2a6vpknyy1fs9liymg6twdaf7fq4fq42bshep0qz5ujyhlkkvcl9" +
		"y1e6408ghni7ukli8s0u4i3dsyxw5tosm3tnkh3rwt090nv5j7e7aedyertf" +
		"a8l62skjqroy6jtlbgprd91oxr89f29ie8cif1ejmcl9yab6atykh63bao7q" +
		"gp1elnyi1tqqxnf1rqqsoigjy98t2rr0tmxk187pmmbmn97iqjs71lfer45s" +
		"xtesb78dmwz3ufp6qtk9njzkc1zqhdgna0flj3fflp7dsudg6q97v8yfk11v" +
		"jurpw5pwnokb2podizibishng2ln8xt795bbesaaygijsu2lslix83kh9h3v" +
		"loj49wc2zrgqe3ojuq7npj4h6c8664mje0wimrpuy805be9xub0jcjomfruo" +
		"hwyuqtaf4k4kqtxsh6skp3u5woxs859mwv6wvhdo32xqd8tvhcl30d1fr0a3" +
		"sjzdulvne9178y2i46wc7hzru3v48u1m5enrqyvqsv7if31p88k9kkp245kx" +
		"br8lwzfql8rsm1w5m7nmqz5lkmchukggxabvhsumeg5xu7iqxf0l3xapf5df" +
		"ufeyct6o721v2507ap7l60o91h0bcelxsrrj3zmduddso0vypidomqp57vev" +
		"aejfgcz9prse8l2yfleny4d9mqcngvqzoobth7e02mmlm9rijgne77dnlsc8" +
		"dzoof9xrbud6ff8vzgoynkncv4sygwpf92g4t75vvf89ifu13lcd8kj4iwsl" +
		"93j2nmudvs4x8vaac3cpvqwvb6nfafqgzsy5lr9w4g11yfp1oew08qpznywu" +
		"1ymtr6ovoulqdbl83j77k3y3vswgkcvuj";
	
	
	
	public static BufferedImage imagen;
	
	static
	{
		try 
		{
			imagen = ImageIO.read(new ByteArrayInputStream(new BigInteger(imagenHormiga, Character.MAX_RADIX).toByteArray()));
		} 
		catch (IOException e1) 
		{
			imagen = null;
		}
	}
	
	public Grafico()
	{
		addMouseListener(this);
	}
	
	@Override
	public synchronized void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 500, 500);
		g.drawImage(imagen, -90, 0, this);
		g.setColor(Color.BLACK);
		for(Ciudad a : ciudades)
		{
			g.fillOval(a.x, a.y, 6, 6);
		}
		if(!modoEdicion)
		{
			g.drawString((int)solucionActual  + "", 400, 400);
			for(int i = 0; i < mejoresActual.size() - 1; i++)
			{
				if(i != 0)
					g.drawLine(ciudades.get(mejoresActual.get(i - 1)).x + 3, ciudades.get(mejoresActual.get(i - 1)).y + 3, ciudades.get(mejoresActual.get(i)).x + 3, ciudades.get(mejoresActual.get(i)).y + 3);
				g.setColor(Color.RED);
				g.drawLine(ciudades.get(mejoresActual.get(i)).x + 3, ciudades.get(mejoresActual.get(i)).y + 3,ciudades.get(mejoresActual.get(i + 1)).x + 3, ciudades.get(mejoresActual.get(i + 1)).y + 3);
				g.setColor(Color.BLACK);
				try 
				{
					Thread.yield();
					Thread.sleep(150);
				}
				catch (InterruptedException e) 
				{
				}
			}
			if(mejoresActual.size() > 1)
				g.drawLine(ciudades.get(mejoresActual.get(mejoresActual.size() - 2)).x + 3, ciudades.get(mejoresActual.get(mejoresActual.size() - 2)).y + 3,ciudades.get(mejoresActual.get(mejoresActual.size() - 1)).x + 3, ciudades.get(mejoresActual.get(mejoresActual.size() - 1)).y + 3);
		}
	}
	
	public synchronized void terminar()
	{
		terminar = true;
	}
	
	public synchronized void iniciar()
	{
		terminar = false;
	}
	
	public synchronized boolean termino()
	{
		return terminar;
	}

	public synchronized void establecerMejorActual(ArrayList<Integer> mejorSolucion, double pesoMejor) 
	{
		mejoresActual = mejorSolucion;
		solucionActual = pesoMejor;
		new Thread(new Runnable()
								{

									@Override
									public void run() 
									{
										paint(getGraphics());
									}
			
								}).start();
	}
	
	public void modoEdicion()
	{
		modoEdicion = true;
		ciudades.clear();
		solucionActual = 0;
		mejoresActual.clear();
		terminar();
		repaint();
	}

	public void modoSolucion()
	{
		modoEdicion = false;
		HiloHormiga hh = new HiloHormiga();
		hh.matrizDistancias = Lectura.generarDistancias(ciudades);
		ModeloHormigasTSP modelo = new ModeloHormigasTSP();
		hh.modelo = modelo;
		iniciar();
		new Thread(hh).start();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
	}
	
	long anterior = 0;
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getWhen() != anterior && modoEdicion)
		{
			anterior = e.getWhen();
			Graphics g = getGraphics();
			g.fillOval(e.getX(), e.getY(), 6, 6);
			Ciudad nueva = new Ciudad(e.getX(), e.getY());
			ciudades.add(nueva);
			repaint();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton boton = (JRadioButton) e.getSource();
		boton.getModel().setSelected(true);
		if(boton.getText().equals("Modo edicion"))
		{
			modoEdicion();
		}
		else
		{
			modoSolucion();
		}
	}
}
